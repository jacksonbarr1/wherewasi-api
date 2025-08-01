package com.wherewasi.wherewasiapi.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Aspect
@Component
@Profile("dev")
public class LoggingAspect {

    public static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {}

    @Pointcut("within(com.wherewasi.wherewasiapi..*)")
    public void applicationPackagePointcut() {
    }

    @Pointcut("execution(* com.wherewasi.wherewasiapi..*.*(..)) && @annotation(org.springframework.cache.annotation.Cacheable)")
    public void cacheableMethodPointcut() {}

    @Around("springBeanPointcut() && applicationPackagePointcut()")
    public Object applicationLogger(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        logger.info("Entering method: {}.{}(){}", className, methodName,
                args.length > 0 ? " with arguments " + Arrays.toString(args) : ".");

        Object object = joinPoint.proceed();

        final int maxLength = 100;
        String resultString = object != null ? object.toString() : "void";
        if (resultString.length() > maxLength) {
            resultString = resultString.substring(0, maxLength) + "... (truncated)";
        }

        logger.info("Exiting method: {}.{}() with result: {}", className, methodName, resultString);

        return object;
    }

    @Before("cacheableMethodPointcut()")
    public void cacheMissLogger(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String cacheName = signature.getMethod()
                .getAnnotation(org.springframework.cache.annotation.Cacheable.class).value()[0];

        logger.info("Cache miss - Fetching data for method: {}.{}() for cache: {}", className, methodName, cacheName);
    }
}

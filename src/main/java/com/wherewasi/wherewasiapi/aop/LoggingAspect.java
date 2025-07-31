package com.wherewasi.wherewasiapi.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Aspect
@Component
public class LoggingAspect {

    public static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {}

    @Pointcut("within(com.wherewasi.wherewasiapi..*)")
    public void applicationPackagePointcut() {
    }

    @Around("springBeanPointcut() && applicationPackagePointcut()")
    public Object applicationLogger(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        logger.info("Entering method: {}.{}(){}", className, methodName,
                args.length > 0 ? " with arguments " + Arrays.toString(args) : ".");

        Object object = joinPoint.proceed();

        logger.info("Exiting method: {}.{}() with result: {}", className, methodName,
                object != null ? object.toString() : "void");

        return object;

    }
}

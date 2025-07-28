package com.wherewasi.wherewasiapi.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class RegisterRequestValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldPassValidationWithValidData() {
        RegisterRequest request = RegisterRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password")
                .build();

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

        assertThat(violations).isEmpty();
    }

    @Test
    void shouldFailValidationWhenNameIsBlank() {
        RegisterRequest request = RegisterRequest.builder()
                .firstName("")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("securePassword123")
                .build();

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

        assertThat(violations).isNotEmpty();

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("firstName") &&
                v.getMessage().equals("First name is required"));
    }

    @Test
    void shouldFailValidationWhenEmailIsBlank() {
        RegisterRequest request = RegisterRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("")
                .password("securePassword123")
                .build();

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

        assertThat(violations).isNotEmpty();

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("email") &&
                v.getMessage().equals("Email is required"));
    }

    @Test
    void shoudlFailValidationWhenEmailisInvalid() {
        RegisterRequest request = RegisterRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("invalid-email")
                .password("password")
                .build();

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

        assertThat(violations).isNotEmpty();

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("email") &&
                v.getMessage().equals("Email must be valid"));
    }

    @Test
    void shouldFailValidatoinWhenPasswordIsBlank() {
        RegisterRequest request = RegisterRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("")
                .build();

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

        assertThat(violations).isNotEmpty();

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("password") &&
                v.getMessage().equals("Password is required"));
    }

    @Test
    void shouldFailValidationWhenPasswordIsTooShort() {
        RegisterRequest request = RegisterRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("short")
                .build();

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

        assertThat(violations).isNotEmpty();

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("password") &&
                v.getMessage().equals("Password must be between 8 and 100 characters"));
    }
}

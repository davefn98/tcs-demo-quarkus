package com.tcs.dto;

import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PersonDTOTest {

    private final Validator validator;

    public PersonDTOTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validPersonDTO_shouldHaveNoViolations() {
        PersonDTO dto = new PersonDTO(
                1, "Juan", "juan123", "juan@mail.com", "1234567", "web.com"
        );
        Set<jakarta.validation.ConstraintViolation<PersonDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void invalidEmail_shouldHaveViolation() {
        PersonDTO dto = new PersonDTO(
                1, "Juan", "juan123", "no-email", "1234567", "web.com"
        );
        Set<jakarta.validation.ConstraintViolation<PersonDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void invalidPhone_shouldHaveViolation() {
        PersonDTO dto = new PersonDTO(
                1, "Juan", "juan123", "juan@mail.com", "123", "web.com"
        );
        Set<jakarta.validation.ConstraintViolation<PersonDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }
}
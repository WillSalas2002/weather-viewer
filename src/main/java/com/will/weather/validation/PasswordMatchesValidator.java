package com.will.weather.validation;

import com.will.weather.dto.RegistrationDto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, RegistrationDto> {

    @Override
    public boolean isValid(RegistrationDto registrationDto, ConstraintValidatorContext context) {
        if (registrationDto.getPassword() == null || registrationDto.getRepeatPassword() == null) {
            return false;
        }
        return Objects.equals(registrationDto.getPassword(), registrationDto.getRepeatPassword());
    }
}

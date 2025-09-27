package com.will.weather.validation;

import com.will.weather.dto.LoginDto;
import com.will.weather.service.impl.LoginServiceImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class CredentialValidator implements Validator {

    private final LoginServiceImpl loginService;

    @Override
    public boolean supports(Class<?> clazz) {
        return LoginDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LoginDto loginDto = (LoginDto) target;
        boolean isCorrect = loginService.checkCredentials(loginDto);
        if (!isCorrect) {
            errors.reject("wrong.creds", "Username or password incorrect");
        }
    }
}

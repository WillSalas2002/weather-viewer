package com.will.weather.exception.handler;

import com.will.weather.constants.AppConstants;
import com.will.weather.dto.LoginDto;
import com.will.weather.dto.RegistrationDto;
import com.will.weather.exception.InvalidCredentialsException;
import com.will.weather.exception.LocationAlreadyExistsException;
import com.will.weather.exception.UserAlreadyExistsException;
import com.will.weather.exception.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = UserNotFoundException.class)
    public ModelAndView handleUserNotFound(UserNotFoundException e) {
        return prepareModelAndView(e);
    }

    @ExceptionHandler(value = InvalidCredentialsException.class)
    public ModelAndView handleInvalidCredentials(InvalidCredentialsException e) {
        return prepareModelAndView(e);
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ModelAndView handleUserAlreadyExists(UserAlreadyExistsException e) {
        log.error(e.getMessage());
        ModelAndView mav = new ModelAndView();
        mav.addObject("errorMessage", e.getMessage());
        mav.addObject("registrationDto", new RegistrationDto());
        mav.setViewName(AppConstants.REGISTRATION_PAGE);
        return mav;
    }

    @ExceptionHandler(value = LocationAlreadyExistsException.class)
    public ModelAndView handleUserAlreadyExists(LocationAlreadyExistsException e) {
        log.error(e.getMessage());
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:" + AppConstants.HOME_PATH);
        return mav;
    }

    @ExceptionHandler(value = Exception.class)
    public ModelAndView handleGlobalException(Exception e) {
        log.error(e.getMessage());
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:" + AppConstants.ERROR_PATH);
        return mav;
    }

    private static ModelAndView prepareModelAndView(RuntimeException e) {
        log.error(e.getMessage());
        ModelAndView mav = new ModelAndView();
        mav.addObject("errorMessage", e.getMessage());
        mav.addObject("loginDto", new LoginDto());
        mav.setViewName(AppConstants.LOGIN_PAGE);
        return mav;
    }
}

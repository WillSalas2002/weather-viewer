package com.will.weather.exception.handler;

import com.will.weather.dto.LoginDto;
import com.will.weather.dto.RegistrationDto;
import com.will.weather.exception.InvalidCredentialsException;
import com.will.weather.exception.LocationAlreadyExistsException;
import com.will.weather.exception.UserAlreadyExistsException;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = InvalidCredentialsException.class)
    public ModelAndView handleInvalidCredentials(InvalidCredentialsException e) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("errorMessage", e.getMessage());
        mav.addObject("loginDto", new LoginDto());
        mav.setViewName("login");
        return mav;
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ModelAndView handleUserAlreadyExists(UserAlreadyExistsException e) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("errorMessage", e.getMessage());
        mav.addObject("registrationDto", new RegistrationDto());
        mav.setViewName("registration");
        return mav;
    }

    @ExceptionHandler(value = LocationAlreadyExistsException.class)
    public ModelAndView handleUserAlreadyExists(LocationAlreadyExistsException e) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/weather");
        return mav;
    }

    @ExceptionHandler(value = Exception.class)
    public ModelAndView handleGlobalException(
            HttpServletRequest request, Exception e) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("url", request.getRequestURL());
        mav.setViewName("redirect:/error");
        return mav;
    }
}

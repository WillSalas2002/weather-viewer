package com.will.weather.controller;

import com.will.weather.constants.AppConstants;
import com.will.weather.dto.RegistrationDto;
import com.will.weather.service.RegistrationService;
import com.will.weather.util.CookieHelper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Slf4j
@Controller
@RequestMapping(AppConstants.AUTH_PATH)
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final CookieHelper cookieHelper;

    @GetMapping(AppConstants.REGISTRATION_PATH)
    public String registration(Model model) {
        model.addAttribute("registrationDto", new RegistrationDto());
        return AppConstants.REGISTRATION_PAGE;
    }

    @PostMapping(AppConstants.REGISTRATION_PATH)
    public String register(
            HttpServletRequest request,
            HttpServletResponse response,
            Model model,
            @Valid RegistrationDto registrationDto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return AppConstants.REGISTRATION_PAGE;
        }
        log.info("User [{}] is trying to register", registrationDto.getUsername());
        UUID sessionId =
                registrationService.registerUser(
                        registrationDto.getUsername(), registrationDto.getPassword());
        cookieHelper.attachCookieToUser(response, sessionId);
        request.getSession().setAttribute(AppConstants.SESSION_NAME, registrationDto.getUsername());
        model.addAttribute("registrationDto", registrationDto);
        return "redirect:" + AppConstants.HOME_PATH;
    }
}

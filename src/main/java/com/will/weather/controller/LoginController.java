package com.will.weather.controller;

import com.will.weather.constants.AppConstants;
import com.will.weather.dto.LoginDto;
import com.will.weather.service.LoginService;
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

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping(AppConstants.AUTH_PATH)
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final CookieHelper cookieHelper;

    @GetMapping(AppConstants.LOGIN_PATH)
    public String login(Model model, HttpServletRequest request) {
        if (isSessionValid(request)) {
            return "redirect:" + AppConstants.HOME_PATH;
        }
        model.addAttribute("loginDto", new LoginDto());
        return AppConstants.LOGIN_PAGE;
    }

    @PostMapping(AppConstants.LOGIN_PATH)
    public String login(
            Model model,
            @Valid LoginDto loginDto,
            BindingResult bindingResult,
            HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            return AppConstants.LOGIN_PAGE;
        }
        UUID sessionId = loginService.login(loginDto);
        cookieHelper.attachCookieToUser(response, sessionId);
        model.addAttribute("username", loginDto.getUsername());

        return "redirect:" + AppConstants.HOME_PATH;
    }

    private boolean isSessionValid(HttpServletRequest request) {
        Optional<String> sessionIdOptional = cookieHelper.readCookie(request);
        return sessionIdOptional.isPresent()
                && !loginService.isSessionExpired(sessionIdOptional.get());
    }
}

package com.will.weather.controller;

import com.will.weather.dto.LoginDto;
import com.will.weather.service.LoginService;

import jakarta.servlet.http.Cookie;
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

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request) {
        Optional<String> sessionIdOptional = readCookie(request);
        if (isSessionValid(sessionIdOptional)) {
            return "redirect:/weather";
        }
        model.addAttribute("loginDto", new LoginDto());
        return "login";
    }

    @PostMapping("/login")
    public String login(
            Model model,
            @Valid LoginDto loginDto,
            BindingResult bindingResult,
            HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            return "login";
        }
        UUID sessionId = loginService.login(loginDto);
        attachCookieToUser(response, sessionId);
        model.addAttribute("username", loginDto.getUsername());
        return "index";
    }

    private boolean isSessionValid(Optional<String> sessionIdOptional) {
        return sessionIdOptional.isPresent()
                && !loginService.isSessionExpired(sessionIdOptional.get());
    }

    private Optional<String> readCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                    .filter(c -> "sessionId".equals(c.getName()))
                    .map(Cookie::getValue)
                    .findAny();
        }
        return Optional.empty();
    }

    private static void attachCookieToUser(HttpServletResponse response, UUID sessionId) {
        Cookie cookie = new Cookie("sessionId", sessionId.toString());
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
    }
}

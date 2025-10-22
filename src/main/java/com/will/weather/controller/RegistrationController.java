package com.will.weather.controller;

import com.will.weather.dto.RegistrationDto;
import com.will.weather.service.RegistrationService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("registrationDto", new RegistrationDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String register(
            HttpServletResponse response,
            Model model,
            @Valid RegistrationDto registrationDto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        UUID sessionId =
                registrationService.registerUser(
                        registrationDto.getUsername(), registrationDto.getPassword());
        attachCookieToUser(response, sessionId);
        model.addAttribute("registrationDto", registrationDto);
        return "redirect:/";
    }

    private static void attachCookieToUser(HttpServletResponse response, UUID sessionId) {
        Cookie cookie = new Cookie("sessionId", sessionId.toString());
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
    }
}

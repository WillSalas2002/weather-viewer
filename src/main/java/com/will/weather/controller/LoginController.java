package com.will.weather.controller;

import com.will.weather.dto.LoginDto;
import com.will.weather.validation.CredentialValidator;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final CredentialValidator credentialValidator;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "login";
    }

    @PostMapping("/login")
    public String login(Model model, @Valid LoginDto loginDto, BindingResult bindingResult) {
        credentialValidator.validate(loginDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "login";
        }
        model.addAttribute("username", loginDto.getUsername());
        return "index";
    }
}

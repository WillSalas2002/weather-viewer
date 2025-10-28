package com.will.weather.controller;

import com.will.weather.constants.ApiPaths;
import com.will.weather.constants.HtmlPages;
import com.will.weather.dto.RegistrationDto;
import com.will.weather.service.RegistrationService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping(ApiPaths.AUTH)
@RequiredArgsConstructor
public class RegistrationController extends BaseController {

    private final RegistrationService registrationService;

    @GetMapping(ApiPaths.REGISTRATION)
    public String registration(Model model) {
        model.addAttribute("registrationDto", new RegistrationDto());
        return HtmlPages.REGISTRATION;
    }

    @PostMapping(ApiPaths.REGISTRATION)
    public String register(
            HttpServletResponse response,
            Model model,
            @Valid RegistrationDto registrationDto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return HtmlPages.REGISTRATION;
        }
        UUID sessionId =
                registrationService.registerUser(
                        registrationDto.getUsername(), registrationDto.getPassword());
        attachCookieToUser(response, sessionId);
        model.addAttribute("registrationDto", registrationDto);
        return "redirect:" + ApiPaths.HOME;
    }
}

package com.will.weather.controller;

import com.will.weather.constants.ApiPaths;
import com.will.weather.constants.HtmlPages;
import com.will.weather.dto.LoginDto;
import com.will.weather.service.LoginService;

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
@RequestMapping(ApiPaths.AUTH)
@RequiredArgsConstructor
public class LoginController extends BaseController {

    private final LoginService loginService;

    @GetMapping(ApiPaths.LOGIN)
    public String login(Model model, HttpServletRequest request) {
        Optional<String> sessionIdOptional = readCookie(request);
        if (sessionIdOptional.isPresent()
                && !loginService.isSessionExpired(sessionIdOptional.get())) {
            return "redirect:" + ApiPaths.HOME;
        }
        model.addAttribute("loginDto", new LoginDto());
        return HtmlPages.LOGIN;
    }

    @PostMapping(ApiPaths.LOGIN)
    public String login(
            Model model,
            @Valid LoginDto loginDto,
            BindingResult bindingResult,
            HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            return HtmlPages.LOGIN;
        }
        UUID sessionId = loginService.login(loginDto);
        attachCookieToUser(response, sessionId);
        model.addAttribute("username", loginDto.getUsername());

        return "redirect:" + ApiPaths.HOME;
    }
}

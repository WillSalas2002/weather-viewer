package com.will.weather.controller;

import com.will.weather.client.WeatherClient;
import com.will.weather.dto.ForecastView;
import com.will.weather.service.LoginService;
import com.will.weather.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/weather")
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final LoginService loginService;
    private final WeatherClient weatherClient;

    @GetMapping
    public String getHomePage(HttpServletRequest request, Model model) {
        Optional<String> sessionIdOptional = readCookie(request);
        if (sessionIdOptional.isPresent() && isSessionValid(sessionIdOptional.get())) {
            List<ForecastView> forecastView =
                    userService.findUserWithLocationsBySession(sessionIdOptional.get());
            model.addAttribute("forecastView", forecastView);
            return "index";
        }
        return "redirect:/auth/login";
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

    private boolean isSessionValid(String sessionId) {
        return !loginService.isSessionExpired(sessionId);
    }
}

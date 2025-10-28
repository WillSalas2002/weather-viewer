package com.will.weather.controller;

import com.will.weather.constants.ApiPaths;
import com.will.weather.constants.HtmlPages;
import com.will.weather.dto.ForecastView;
import com.will.weather.service.LoginService;
import com.will.weather.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(ApiPaths.HOME)
@RequiredArgsConstructor
public class HomeController extends BaseController {

    private final UserService userService;
    private final LoginService loginService;

    @GetMapping
    public String getHomePage(HttpServletRequest request, Model model) {
        Optional<String> sessionIdOptional = readCookie(request);
        if (sessionIdOptional.isPresent()
                && !loginService.isSessionExpired(sessionIdOptional.get())) {
            List<ForecastView> forecastView =
                    userService.findUserWithLocationsBySession(sessionIdOptional.get());

            model.addAttribute("forecastView", forecastView);
            return HtmlPages.HOME;
        }
        return "redirect:" + ApiPaths.AUTH + ApiPaths.LOGIN;
    }
}

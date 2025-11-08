package com.will.weather.controller;

import com.will.weather.constants.AppConstants;
import com.will.weather.dto.ForecastView;
import com.will.weather.service.LocationService;
import com.will.weather.service.LoginService;
import com.will.weather.util.CookieHelper;

import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(AppConstants.HOME_PATH)
@RequiredArgsConstructor
public class HomeController {

    private final LocationService locationService;
    private final LoginService loginService;
    private final CookieHelper cookieHelper;

    @GetMapping
    public String getHomePage(HttpServletRequest request, Model model) {
        if (isSessionValid(request)) {
            List<ForecastView> forecastView =
                    locationService.findLocations(
                            (String) request.getSession().getAttribute(AppConstants.SESSION_NAME));

            model.addAttribute("forecastView", forecastView);
            model.addAttribute(
                    AppConstants.SESSION_NAME,
                    request.getSession().getAttribute(AppConstants.SESSION_NAME));

            return AppConstants.HOME_PAGE;
        }
        return "redirect:" + AppConstants.AUTH_PATH + AppConstants.LOGIN_PATH;
    }

    private boolean isSessionValid(HttpServletRequest request) {
        Optional<String> sessionIdOptional = cookieHelper.readCookie(request);
        return sessionIdOptional.isPresent()
                && !loginService.isSessionExpired(sessionIdOptional.get());
    }
}

/*
    1) converting temperature from faranheit to celcius
    4) deleting a location from user
    2) loading corresponding weather icons
    3) Cover code with tests
*/
package com.will.weather.controller;

import com.will.weather.constants.AppConstants;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(AppConstants.AUTH_PATH)
@RequiredArgsConstructor
public class LogoutController {

    @GetMapping(AppConstants.LOGOUT_PATH)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        log.info("User [{}] is logging out.", session.getAttribute(AppConstants.SESSION_NAME));
        Cookie cookie = new Cookie(AppConstants.COOKIE_NAME, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        log.info("User [{}] has logged out from the system.", session.getAttribute(AppConstants.SESSION_NAME));
        session.invalidate();
        return "redirect:" + AppConstants.AUTH_PATH + AppConstants.LOGIN_PATH;
    }
}

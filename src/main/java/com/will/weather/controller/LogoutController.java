package com.will.weather.controller;

import com.will.weather.constants.ApiPaths;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(ApiPaths.AUTH)
@RequiredArgsConstructor
public class LogoutController {
    
    @GetMapping(ApiPaths.LOGOUT)
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("sessionId", "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:" + ApiPaths.AUTH + ApiPaths.LOGIN;
    }
}

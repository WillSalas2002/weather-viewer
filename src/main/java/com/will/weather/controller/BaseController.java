package com.will.weather.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public abstract class BaseController {

    protected Optional<String> readCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                    .filter(c -> "sessionId".equals(c.getName()))
                    .map(Cookie::getValue)
                    .findAny();
        }
        return Optional.empty();
    }

    protected void attachCookieToUser(HttpServletResponse response, UUID sessionId) {
        Cookie cookie = new Cookie("sessionId", sessionId.toString());
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
    }
}

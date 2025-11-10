package com.will.weather.util;

import com.will.weather.constants.AppConstants;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Component
public class CookieHelper {

    public Optional<String> readCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                    .filter(c -> AppConstants.COOKIE_NAME.equals(c.getName()))
                    .map(Cookie::getValue)
                    .findAny();
        }
        return Optional.empty();
    }

    public void attachCookieToUser(HttpServletResponse response, UUID sessionId) {
        Cookie cookie = new Cookie(AppConstants.COOKIE_NAME, sessionId.toString());
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
    }
}

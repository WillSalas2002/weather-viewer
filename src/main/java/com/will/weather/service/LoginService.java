package com.will.weather.service;

import com.will.weather.dto.LoginDto;

public interface LoginService {
    boolean checkCredentials(LoginDto loginDto);

    boolean isSessionExpired(String cookie);
}

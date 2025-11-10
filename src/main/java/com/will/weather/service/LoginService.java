package com.will.weather.service;

import com.will.weather.dto.LoginDto;

import java.util.UUID;

public interface LoginService {

    boolean isSessionExpired(String cookie);

    UUID login(LoginDto loginDto);
}

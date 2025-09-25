package com.will.weather.service;

import com.will.weather.dto.LoginDto;

import java.util.Optional;

public interface LoginService {
    Optional<String> login(LoginDto loginDto);
}

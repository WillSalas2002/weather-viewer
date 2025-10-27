package com.will.weather.service;

import com.will.weather.dto.ForecastView;

import java.util.List;

public interface UserService {

    List<ForecastView> findUserWithLocationsBySession(String sessionId);
}

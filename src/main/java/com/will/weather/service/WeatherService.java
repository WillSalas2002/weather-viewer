package com.will.weather.service;

import com.will.weather.client.dto.LocationResponse;
import com.will.weather.dto.ForecastView;

import java.util.List;

public interface WeatherService {

    List<ForecastView> findLocations(String login);

    List<LocationResponse> getAll(String name);
}

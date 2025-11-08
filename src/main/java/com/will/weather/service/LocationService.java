package com.will.weather.service;

import com.will.weather.client.dto.LocationResponse;
import com.will.weather.dto.ForecastView;
import com.will.weather.dto.LocationDto;

import java.util.List;

public interface LocationService {

    List<LocationResponse> getAll(String name);

    void save(LocationDto location, String login);

    List<ForecastView> findLocations(String login);
}

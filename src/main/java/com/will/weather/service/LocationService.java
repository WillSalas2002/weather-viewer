package com.will.weather.service;

import com.will.weather.client.dto.LocationResponse;
import com.will.weather.dto.AddLocationDto;
import com.will.weather.dto.ForecastView;
import com.will.weather.dto.RemoveLocationDto;

import java.util.List;

public interface LocationService {

    List<LocationResponse> getAll(String name);

    void save(AddLocationDto location, String login);

    List<ForecastView> findLocations(String login);

    void remove(RemoveLocationDto removeLocationDto);
}

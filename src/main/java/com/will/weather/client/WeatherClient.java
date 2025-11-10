package com.will.weather.client;

import com.will.weather.client.dto.ForecastDto;
import com.will.weather.client.dto.LocationResponse;

import java.math.BigDecimal;
import java.util.List;

public interface WeatherClient {

    List<LocationResponse> getLocations(String name);

    ForecastDto getForecastByLocation(BigDecimal lat, BigDecimal lon);
}

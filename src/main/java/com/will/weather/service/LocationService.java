package com.will.weather.service;

import com.will.weather.client.dto.LocationDto;

import java.util.List;

public interface LocationService {

    List<LocationDto> getAll(String name);

}

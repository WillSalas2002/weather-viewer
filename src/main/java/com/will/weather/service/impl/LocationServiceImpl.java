package com.will.weather.service.impl;

import com.will.weather.client.WeatherClient;
import com.will.weather.client.dto.LocationResponse;
import com.will.weather.dto.LocationDto;
import com.will.weather.repository.LocationRepository;
import com.will.weather.service.LocationService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    
    private final WeatherClient weatherClient;
    private final LocationRepository locationRepository;
    
    @Override
    public List<LocationResponse> getAll(String name) {
        return weatherClient.getLocations(name);
    }

    @Override
    public void save(LocationDto location) {

    }
}

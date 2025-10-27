package com.will.weather.service.impl;

import com.will.weather.client.WeatherClient;
import com.will.weather.client.dto.LocationDto;
import com.will.weather.service.LocationService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    
    private final WeatherClient weatherClient;
    
    @Override
    public List<LocationDto> getAll(String name) {
        return weatherClient.getLocations(name);
    }
}

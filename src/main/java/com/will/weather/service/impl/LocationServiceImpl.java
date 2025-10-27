package com.will.weather.service.impl;

import com.will.weather.client.WeatherClient;
import com.will.weather.client.dto.LocationResponse;
import com.will.weather.dto.LocationDto;
import com.will.weather.model.Location;
import com.will.weather.repository.LocationRepository;
import com.will.weather.repository.UserRepository;
import com.will.weather.service.LocationService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final WeatherClient weatherClient;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    @Override
    public List<LocationResponse> getAll(String name) {
        return weatherClient.getLocations(name);
    }

    @Override
    public void save(LocationDto location, String sessionId) {
        Long userId =
                userRepository
                        .findUserIdBySessionId(UUID.fromString(sessionId))
                        .orElseThrow(
                                () ->
                                        new NoSuchElementException(
                                                String.format(
                                                        "Session with id [%s] has no associated user",
                                                        sessionId)));
        locationRepository.save(
                Location.builder()
                        .withUserId(userId)
                        .withName(location.getName())
                        .withLongitude(location.getLongitude())
                        .withLatitude(location.getLatitude())
                        .build());
    }
}

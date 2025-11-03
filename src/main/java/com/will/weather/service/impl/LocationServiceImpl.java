package com.will.weather.service.impl;

import com.will.weather.client.WeatherClient;
import com.will.weather.client.dto.ForecastDto;
import com.will.weather.client.dto.LocationResponse;
import com.will.weather.dto.ForecastView;
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

    @Override
    public List<ForecastView> findLocations(String username) {

        List<Location> locations = locationRepository.findLocationsByUsername(username);
        return locations.stream()
                .map(
                        location -> {
                            ForecastDto forecastDto =
                                    weatherClient.getForecastByLocation(
                                            location.getLatitude(), location.getLongitude());
                            return mapToForecastView(forecastDto);
                        })
                .toList();
    }

    // TODO: need to convert from farangeit to celcius
    private ForecastView mapToForecastView(ForecastDto forecastDto) {
        return new ForecastView(
                forecastDto.main().temp(),
                forecastDto.main().feelsLike(),
                forecastDto.main().humidity(),
                forecastDto.weathers().getFirst().main(),
                forecastDto.weathers().getFirst().description(),
                forecastDto.sys().country(),
                forecastDto.sys().sunrise(),
                forecastDto.sys().sunset(),
                forecastDto.timezone(),
                forecastDto.name());
    }
}

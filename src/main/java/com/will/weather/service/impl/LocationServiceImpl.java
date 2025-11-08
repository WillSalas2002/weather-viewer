package com.will.weather.service.impl;

import com.will.weather.client.WeatherClient;
import com.will.weather.client.dto.ForecastDto;
import com.will.weather.client.dto.LocationResponse;
import com.will.weather.dto.ForecastView;
import com.will.weather.dto.LocationDto;
import com.will.weather.exception.UserNotFoundException;
import com.will.weather.model.Location;
import com.will.weather.repository.LocationRepository;
import com.will.weather.repository.UserRepository;
import com.will.weather.service.LocationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
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
    @Transactional
    public void save(LocationDto locationDto, String login) {
        Long userId =
                userRepository
                        .findUserIdByLogin(login)
                        .orElseThrow(
                                () -> {
                                    log.warn("User [{}] not found", login);
                                    return new UserNotFoundException(
                                            String.format("No such user with login %s", login));
                                });
        log.info("Adding location [{}] to the user [{}]", locationDto.getName(), login);
        locationRepository.save(
                Location.builder()
                        .withUserId(userId)
                        .withName(locationDto.getName())
                        .withLatitude(locationDto.getLatitude())
                        .withLongitude(locationDto.getLongitude())
                        .build());
        log.info("Successfully added location [{}] to the user [{}]", locationDto.getName(), login);
    }

    @Override
    public List<ForecastView> findLocations(String login) {
        List<Location> locations = locationRepository.findLocationsByUsername(login);
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

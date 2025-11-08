package com.will.weather.service.impl;

import com.will.weather.client.WeatherClientImpl;
import com.will.weather.client.dto.ForecastDto;
import com.will.weather.client.dto.LocationResponse;
import com.will.weather.constants.AppConstants;
import com.will.weather.dto.AddLocationDto;
import com.will.weather.dto.ForecastView;
import com.will.weather.dto.RemoveLocationDto;
import com.will.weather.exception.UserNotFoundException;
import com.will.weather.model.Location;
import com.will.weather.repository.LocationRepository;
import com.will.weather.repository.UserRepository;
import com.will.weather.service.LocationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final WeatherClientImpl weatherClientImpl;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    @Override
    public List<LocationResponse> getAll(String name) {
        return weatherClientImpl.getLocations(name);
    }

    @Override
    @Transactional
    public void save(AddLocationDto addLocationDto, String login) {
        Long userId =
                userRepository
                        .findUserIdByLogin(login)
                        .orElseThrow(
                                () -> {
                                    log.warn("User [{}] not found", login);
                                    return new UserNotFoundException(
                                            String.format("No such user with login %s", login));
                                });
        log.info("Adding location [{}] to the user [{}]", addLocationDto.getName(), login);
        locationRepository.save(
                Location.builder()
                        .withUserId(userId)
                        .withName(addLocationDto.getName())
                        .withLatitude(addLocationDto.getLatitude())
                        .withLongitude(addLocationDto.getLongitude())
                        .build());
        log.info(
                "Successfully added location [{}] to the user [{}]",
                addLocationDto.getName(),
                login);
    }

    @Override
    public List<ForecastView> findLocations(String login) {
        List<Location> locations = locationRepository.findLocationsByUsername(login);
        return locations.stream()
                .map(
                        location -> {
                            ForecastDto forecastDto =
                                    weatherClientImpl.getForecastByLocation(
                                            location.getLatitude(), location.getLongitude());
                            return mapToForecastView(forecastDto, location);
                        })
                .toList();
    }

    @Override
    public void remove(RemoveLocationDto removeLocationDto) {
        locationRepository.deleteLocationByLogin(
                removeLocationDto.getLogin(),
                removeLocationDto.getLatitude(),
                removeLocationDto.getLongitude());
    }

    private ForecastView mapToForecastView(ForecastDto forecastDto, Location location) {
        return new ForecastView(
                location.getLongitude(),
                location.getLatitude(),
                convertToCelsius(forecastDto.main().temp()),
                convertToCelsius(forecastDto.main().feelsLike()),
                forecastDto.main().humidity(),
                forecastDto.weathers().getFirst().main(),
                forecastDto.weathers().getFirst().description(),
                forecastDto.sys().country(),
                forecastDto.sys().sunrise(),
                forecastDto.sys().sunset(),
                forecastDto.timezone(),
                forecastDto.name());
    }

    private static double convertToCelsius(double kelvin) {
        DecimalFormat df = new DecimalFormat("#.00");
        return Double.parseDouble(df.format(kelvin - AppConstants.KELVIN_TO_CELSIUS_OFFSET));
    }
}

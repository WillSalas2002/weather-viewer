package com.will.weather.service.impl;

import com.will.weather.client.WeatherClient;
import com.will.weather.client.dto.ForecastDto;
import com.will.weather.dto.ForecastView;
import com.will.weather.dto.UserLocationData;
import com.will.weather.repository.UserRepository;
import com.will.weather.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final WeatherClient weatherClient;

    @Override
    public List<ForecastView> findUserWithLocationsBySession(String sessionId) {
        Optional<Long> userIdOptional =
                userRepository.findUserBySessionId(UUID.fromString(sessionId));
        if (userIdOptional.isEmpty()) {
            throw new NoSuchElementException(
                    String.format("Session with id [%s] has no associated user", sessionId));
        }

        UserLocationData userLocationData =
                userRepository.findUserWithLocationsByUserId(userIdOptional.get());

        return userLocationData.locations().stream()
                .map(
                        location -> {
                            ForecastDto forecastDto =
                                    weatherClient.getForecastByLocation(
                                            location.latitude(), location.longitude());
                            return mapToForecastView(userLocationData, forecastDto);
                        })
                .toList();
    }

    private ForecastView mapToForecastView(
            UserLocationData userLocationData, ForecastDto forecastDto) {
        return new ForecastView(
                userLocationData.login(),
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

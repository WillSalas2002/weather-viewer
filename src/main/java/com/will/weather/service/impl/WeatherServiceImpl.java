package com.will.weather.service.impl;

import com.will.weather.client.WeatherClient;
import com.will.weather.client.dto.ForecastDto;
import com.will.weather.client.dto.LocationResponse;
import com.will.weather.constants.AppConstants;
import com.will.weather.dto.ForecastView;
import com.will.weather.model.Location;
import com.will.weather.repository.LocationRepository;
import com.will.weather.service.WeatherService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    @Value("${weather.url.icon}")
    private String templatedWeatherIconUrl;

    private final WeatherClient weatherClient;
    private final LocationRepository locationRepository;

    @Override
    public List<LocationResponse> getAll(String name) {
        String validName = name.trim().replace(" ", "-");
        return weatherClient.getLocations(validName);
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
                            return mapToForecastView(forecastDto, location);
                        })
                .toList();
    }


    private ForecastView mapToForecastView(ForecastDto forecastDto, Location location) {
        return new ForecastView(
                String.format(templatedWeatherIconUrl, forecastDto.weathers().get(0).icon()),
                location.getLongitude(),
                location.getLatitude(),
                convertToCelsius(forecastDto.main().temp()),
                convertToCelsius(forecastDto.main().feelsLike()),
                forecastDto.main().humidity(),
                forecastDto.weathers().get(0).main(),
                forecastDto.weathers().get(0).description(),
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

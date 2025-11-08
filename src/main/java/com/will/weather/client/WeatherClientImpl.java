package com.will.weather.client;

import com.will.weather.client.dto.ForecastDto;
import com.will.weather.client.dto.LocationResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherClientImpl implements WeatherClient {

    @Value("${weather.api-key}")
    private String apiKey;

    @Value("${weather.url.coordinates}")
    private String templatedCoordinateUrl;

    @Value("${weather.url.forecast}")
    private String templatedForecastUrl;

    @Value("${weather.quantity.limit}")
    private int limit;

    private final RestTemplate restTemplate;

    @Override
    public List<LocationResponse> getLocations(String name) {
        String urlStr = String.format(templatedCoordinateUrl, name, limit, apiKey);
        log.info("Sending request to weather client for getting coordinates of [{}]", name);
        ResponseEntity<List<LocationResponse>> response =
                restTemplate.exchange(
                        URI.create(urlStr),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {});

        if (!HttpStatus.OK.equals(response.getStatusCode())) {
            log.error("Error when trying to send request to weather client for [{}]", name);
            throw new IllegalStateException(
                    String.format("Location with name [%s] not found.", name));
        }
        log.info("Weather client returned [{}] records for [{}]", response.getBody().size(), name);
        return response.getBody();
    }

    @Override
    public ForecastDto getForecastByLocation(BigDecimal lat, BigDecimal lon) {
        log.info(
                "Sending request to weather client for getting forecast info for coordinate with [lon: {}] and [lat: {}]",
                lon,
                lat);
        String url = String.format(templatedForecastUrl, lat, lon, apiKey);

        ResponseEntity<ForecastDto> response = restTemplate.getForEntity(url, ForecastDto.class);

        if (!HttpStatus.OK.equals(response.getStatusCode())) {
            log.error(
                    "Error when trying to send request to weather client for getting forecast by coordinates, lon [{}], lat [{}]",
                    lon,
                    lat);
            throw new IllegalStateException(
                    String.format(
                            "Couldn't load forecast data for Coordinate lat=[%s] and lon=[%s].",
                            lat, lon));
        }
        log.info("Weather client returned successful response for forecast request");
        return response.getBody();
    }
}

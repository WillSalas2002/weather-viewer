package com.will.weather.client;

import com.will.weather.client.dto.ForecastDto;
import com.will.weather.client.dto.LocationDto;

import lombok.RequiredArgsConstructor;

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
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class WeatherClient {

    @Value("${weather.api-key}")
    private String apiKey;

    @Value("${weather.url.coordinates}")
    private String templatedCoordinateUrl;

    @Value("${weather.url.forecast}")
    private String templatedForecastUrl;

    @Value("${weather.quantity.limit}")
    private int limit;

    private final RestTemplate restTemplate;

    public List<LocationDto> getLocations(String name) {
        String urlStr = String.format(templatedCoordinateUrl, name, limit, apiKey);

        ResponseEntity<List<LocationDto>> response =
                restTemplate.exchange(
                        URI.create(urlStr),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {});

        if (!HttpStatus.OK.equals(response.getStatusCode())) {
            throw new NoSuchElementException(
                    String.format("Location with name [%s] not found.", name));
        }
        return response.getBody();
    }

    public ForecastDto getForecastByLocation(BigDecimal lat, BigDecimal lon) {
        String url = String.format(templatedForecastUrl, lat, lon, apiKey);

        ResponseEntity<ForecastDto> response = restTemplate.getForEntity(url, ForecastDto.class);

        if (!HttpStatus.OK.equals(response.getStatusCode())) {
            throw new NoSuchElementException(
                    String.format(
                            "Couldn't load forecast data for Coordinate lat=[%s] and lon=[%s].",
                            lat, lon));
        }
        return response.getBody();
    }
}

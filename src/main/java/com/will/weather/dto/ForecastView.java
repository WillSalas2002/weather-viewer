package com.will.weather.dto;

import java.math.BigDecimal;

public record ForecastView(
        String iconUrl,
        BigDecimal longitude,
        BigDecimal latitude,
        double temp,
        double feelsLike,
        int humidity,
        String main,
        String description,
        String country,
        String sunrise,
        String sunset,
        String timezone,
        String name) {}

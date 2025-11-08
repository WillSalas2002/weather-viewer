package com.will.weather.dto;

import java.math.BigDecimal;

public record ForecastView(
        BigDecimal longitude,
        BigDecimal latitude,
        double temp,
        double feelsLike,
        double humidity,
        String main,
        String description,
        String country,
        String sunrise,
        String sunset,
        String timezone,
        String name) {}

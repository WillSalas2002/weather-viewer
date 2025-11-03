package com.will.weather.dto;

public record ForecastView(
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

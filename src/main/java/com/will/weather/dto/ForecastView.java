package com.will.weather.dto;

public record ForecastView(
        String login,
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

package com.will.weather.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ForecastDto(
        @JsonProperty("main") Main main,
        @JsonProperty("weather") List<Weather> weathers,
        @JsonProperty("sys") Sys sys,
        @JsonProperty("timezone") String timezone,
        @JsonProperty("name") String name) {

    public record Main(
            @JsonProperty("temp") double temp,
            @JsonProperty("feels_like") double feelsLike,
            @JsonProperty("humidity") int humidity) {}

    public record Weather(
            @JsonProperty("main") String main, @JsonProperty("description") String description) {}

    public record Sys(
            @JsonProperty("country") String country,
            @JsonProperty("sunrise") String sunrise,
            @JsonProperty("sunset") String sunset) {}
}

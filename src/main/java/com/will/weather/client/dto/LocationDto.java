package com.will.weather.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LocationDto(
        String name,
        @JsonProperty("lon") BigDecimal longitude,
        @JsonProperty("lat") BigDecimal latitude,
        String country,
        String state) {}

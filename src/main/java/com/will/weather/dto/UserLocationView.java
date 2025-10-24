package com.will.weather.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder(setterPrefix = "with")
public record UserLocationView(String login, List<LocationView> locations) {
    @Builder(setterPrefix = "with")
    public record LocationView(String locationName, BigDecimal latitude, BigDecimal longitude) {}
}

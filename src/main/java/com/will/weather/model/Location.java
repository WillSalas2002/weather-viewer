package com.will.weather.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(setterPrefix = "with")
public class Location {
    private Long id;
    private String name;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private Long userId;
}

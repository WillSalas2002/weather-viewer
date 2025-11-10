package com.will.weather.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddLocationDto {
    private String name;
    private BigDecimal longitude;
    private BigDecimal latitude;
}

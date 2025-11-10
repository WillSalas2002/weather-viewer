package com.will.weather.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RemoveLocationDto {
    private String login;
    private BigDecimal longitude;
    private BigDecimal latitude;
}

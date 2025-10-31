package com.will.weather.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(setterPrefix = "with")
public class User {
    private Long id;
    private String login;
    private String password;
    private Set<Long> locationIds;
}

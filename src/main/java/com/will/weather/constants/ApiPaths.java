package com.will.weather.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiPaths {
    public static final String AUTH = "/auth";
    public static final String LOGIN = "/login";
    public static final String REGISTRATION = "/registration";
    public static final String LOGOUT = AUTH + "/logout";
    public static final String HOME = "/weather";
    public static final String LOCATION = "/locations";
    public static final String ADD_LOCATION = "/add";
    public static final String ERROR = "/error";
}

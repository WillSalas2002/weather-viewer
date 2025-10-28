package com.will.weather.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AppConstants {
    // API paths
    public static final String AUTH_PATH = "/auth";
    public static final String LOGIN_PATH = "/login";
    public static final String REGISTRATION_PATH = "/registration";
    public static final String LOGOUT_PATH = AUTH_PATH + "/logout";
    public static final String HOME_PATH = "/weather";
    public static final String LOCATION_PATH = "/locations";
    public static final String ADD_LOCATION_PATH = "/add";
    public static final String ERROR_PATH = "/error";

    // HTML pages
    public static final String LOGIN_PAGE = "login";
    public static final String REGISTRATION_PAGE = "registration";
    public static final String ERROR_PAGE = "error";
    public static final String SEARCH_RESULTS_PAGE = "search-results";
    public static final String HOME_PAGE = "index";

    // Cookie names
    public static final String COOKIE_NAME = "sessionId";
}

package com.will.weather.service;

import java.util.UUID;

public interface RegistrationService {

    UUID registerUser(String username, String password);
}

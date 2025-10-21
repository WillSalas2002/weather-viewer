package com.will.weather.repository;

import com.will.weather.model.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByUsernameAndPassword(String username, String password);

    Long save(User user);
}

package com.will.weather.repository;

import com.will.weather.model.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByLogin(String login);

    Long save(User user);

    Optional<Long> findUserIdByLogin(String login);
}

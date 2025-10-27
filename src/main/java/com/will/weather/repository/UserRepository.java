package com.will.weather.repository;

import com.will.weather.dto.UserLocationData;
import com.will.weather.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    Optional<User> findByUsernameAndPassword(String username, String password);

    Long save(User user);

    UserLocationData findUserWithLocationsByUserId(Long userId);

    Optional<Long> findUserBySessionId(UUID uuid);
}

package com.will.weather.repository.impl;

import com.will.weather.model.User;
import com.will.weather.repository.UserRepository;
import com.will.weather.repository.mapper.UserRowMapper;

import lombok.AllArgsConstructor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private static final String SQL_BY_USERNAME_AND_PASSWORD =
            "SELECT u.login, u.password FROM users u WHERE u.login = ? AND u.password = ?";
    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        List<User> users =
                jdbcTemplate.query(SQL_BY_USERNAME_AND_PASSWORD, userRowMapper, username, password);
        return users.stream().findFirst();
    }
}

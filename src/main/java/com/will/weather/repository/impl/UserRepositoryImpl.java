package com.will.weather.repository.impl;

import com.will.weather.exception.UserAlreadyExistsException;
import com.will.weather.model.User;
import com.will.weather.repository.UserRepository;
import com.will.weather.repository.mapper.UserRowMapper;

import lombok.AllArgsConstructor;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;

    private static final String SQL_BY_USERNAME_AND_PASSWORD =
            "SELECT u.login, u.password FROM users u WHERE u.login = ? AND u.password = ?";
    private static final String SQL_INSERT = "INSERT INTO users (login, password) VALUES (?, ?)";

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        List<User> users =
                jdbcTemplate.query(SQL_BY_USERNAME_AND_PASSWORD, userRowMapper, username, password);
        return users.stream().findFirst();
    }

    @Override
    public Long save(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps =
                                connection.prepareStatement(SQL_INSERT, new String[] {"id"});
                        ps.setString(1, user.getLogin());
                        ps.setString(2, user.getPassword());
                        return ps;
                    },
                    keyHolder);
            return keyHolder.getKeyAs(Long.class);
        } catch (DuplicateKeyException e) {
            throw new UserAlreadyExistsException(
                    String.format("Username [%s] is already taken", user.getLogin()));
        }
    }
}

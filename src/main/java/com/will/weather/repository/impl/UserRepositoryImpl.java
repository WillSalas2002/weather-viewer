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

    private static final String SQL_BY_USERNAME_AND_PASSWORD =
            "SELECT u.id, u.login, u.password FROM users u WHERE u.login = ?";
    private static final String SQL_INSERT = "INSERT INTO users (login, password) VALUES (?, ?)";
    private static final String SQL_FIND_USER_ID_BY_LOGIN =
            "SELECT u.id FROM users u WHERE u.login = ?";

    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;

    @Override
    public Optional<User> findByLogin(String login) {
        return jdbcTemplate.query(SQL_BY_USERNAME_AND_PASSWORD, userRowMapper, login).stream()
                .findFirst();
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
            return new Long(keyHolder.getKey().intValue());
        } catch (DuplicateKeyException e) {
            throw new UserAlreadyExistsException(
                    String.format("Username [%s] is already taken", user.getLogin()));
        }
    }

    @Override
    public Optional<Long> findUserIdByLogin(String login) {
        List<Long> ids =
                jdbcTemplate.query(
                        SQL_FIND_USER_ID_BY_LOGIN, (rs, rowNum) -> rs.getLong("id"), login);

        return ids.stream().findFirst();
    }
}

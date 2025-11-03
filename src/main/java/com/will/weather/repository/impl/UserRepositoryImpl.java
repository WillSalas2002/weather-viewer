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
import java.util.UUID;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;

    private static final String SQL_BY_USERNAME_AND_PASSWORD =
            "SELECT u.id, u.login, u.password FROM users u WHERE u.login = ? AND u.password = ?";
    private static final String SQL_INSERT = "INSERT INTO users (login, password) VALUES (?, ?)";
    private static final String SQL_FIND_USER_ID_BY_SESSION_ID =
"""
SELECT u."id" FROM "public".users u
JOIN "public".sessions s ON u.id = s.user_id
WHERE s."id" = ?;
""";
    private static final String SQL_FIND_USER_WITH_LOCATIONS_BY_USER_ID =
"""
SELECT l."name", l.latitude, l.longitude, u.login
FROM users u
JOIN locations l ON u.id = l.user_id
WHERE u.id = ?""";

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
            return new Long(keyHolder.getKey().intValue());
        } catch (DuplicateKeyException e) {
            throw new UserAlreadyExistsException(
                    String.format("Username [%s] is already taken", user.getLogin()));
        }
    }

    @Override
    public Optional<Long> findUserIdBySessionId(UUID sessionId) {
        List<Long> ids =
                jdbcTemplate.query(
                        SQL_FIND_USER_ID_BY_SESSION_ID,
                        (rs, rowNum) -> rs.getLong("id"),
                        sessionId);

        return ids.stream().findFirst();
    }
}

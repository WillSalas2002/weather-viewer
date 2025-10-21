package com.will.weather.repository.impl;

import com.will.weather.model.Session;
import com.will.weather.repository.SessionRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SessionRepositoryImpl implements SessionRepository {

    private final JdbcTemplate jdbcTemplate;

    public static final String SQL_INSERT =
            "INSERT INTO sessions (id, user_id, expires_at) VALUES (?, ?, ?)";

    @Override
    public void save(Session session) {
        jdbcTemplate.update(
                SQL_INSERT, session.getUuid(), session.getUser().getId(), session.getExpiresAt());
    }
}

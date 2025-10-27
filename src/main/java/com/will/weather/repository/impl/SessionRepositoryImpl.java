package com.will.weather.repository.impl;

import com.will.weather.model.Session;
import com.will.weather.repository.SessionRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class SessionRepositoryImpl implements SessionRepository {

    public static final String SQL_FIND_EXP_BY_SESSION_ID =
            "SELECT s.expires_at FROM sessions s WHERE s.id = ?";
    private final JdbcTemplate jdbcTemplate;

    public static final String SQL_INSERT =
            "INSERT INTO sessions (id, user_id, expires_at) VALUES (?, ?, ?)";

    @Override
    public void save(Session session) {
        jdbcTemplate.update(
                SQL_INSERT, session.getUuid(), session.getUserId(), session.getExpiresAt());
    }

    @Override
    public LocalDateTime findExpirationDateBySessionId(String sessionId) {
        return jdbcTemplate.queryForObject(
                SQL_FIND_EXP_BY_SESSION_ID, LocalDateTime.class, UUID.fromString(sessionId));
    }
}

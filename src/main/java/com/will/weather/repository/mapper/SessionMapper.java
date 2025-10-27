package com.will.weather.repository.mapper;

import com.will.weather.model.Session;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

public class SessionMapper implements RowMapper<Session> {
    @Override
    public Session mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Session.builder()
                .withUuid(rs.getObject("id", UUID.class))
                .withUserId(rs.getLong("user_id"))
                .withExpiresAt(rs.getObject("expires_at", LocalDateTime.class))
                .build();
    }
}

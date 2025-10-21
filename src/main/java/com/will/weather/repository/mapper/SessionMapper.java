package com.will.weather.repository.mapper;

import com.will.weather.model.Session;
import com.will.weather.model.User;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

public class SessionMapper implements RowMapper<Session> {
    @Override
    public Session mapRow(ResultSet rs, int rowNum) throws SQLException {
        Session session = new Session();
        session.setUuid(rs.getObject("id", UUID.class));
        session.setUser(new User(rs.getLong("user_id")));
        session.setExpiresAt(rs.getObject("expires_at", LocalDateTime.class));
        return session;
    }
}

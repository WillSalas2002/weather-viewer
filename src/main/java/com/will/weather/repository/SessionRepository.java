package com.will.weather.repository;

import com.will.weather.model.Session;

import java.time.LocalDateTime;

public interface SessionRepository {

    void save(Session session);

    LocalDateTime findExpirationDateBySessionId(String sessionId);
}

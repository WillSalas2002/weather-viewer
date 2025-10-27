package com.will.weather.repository.impl;

import com.will.weather.exception.LocationAlreadyExistsException;
import com.will.weather.model.Location;
import com.will.weather.repository.LocationRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LocationRepositoryImpl implements LocationRepository {

    private static final String SQL_INSERT =
"""
    INSERT INTO locations(latitude, longitude, "name", user_id)
    VALUES (?, ?, ?, ?)
""";
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(Location location) {
        try {
            jdbcTemplate.update(
                    SQL_INSERT,
                    location.getLatitude(),
                    location.getLongitude(),
                    location.getName(),
                    location.getUserId());
        } catch (DuplicateKeyException e) {
            throw new LocationAlreadyExistsException(
                    String.format("Location [%s] is already added.", location.getName()));
        }
    }
}

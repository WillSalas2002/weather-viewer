package com.will.weather.repository.impl;

import com.will.weather.exception.LocationAlreadyExistsException;
import com.will.weather.model.Location;
import com.will.weather.repository.LocationRepository;
import com.will.weather.repository.mapper.LocationMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LocationRepositoryImpl implements LocationRepository {

    private static final String SQL_INSERT =
"""
    INSERT INTO locations(latitude, longitude, "name", user_id)
    VALUES (?, ?, ?, ?)
""";
    private static final String SQL_SELECT =
"""
    SELECT l."name", l.longitude, l.latitude
    FROM locations l
    JOIN users u ON l.user_id = u.id
    WHERE u.login = ?
""";
    private final LocationMapper locationMapper;
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

    @Override
    public List<Location> findLocationsByUsername(String username) {
        return jdbcTemplate.query(SQL_SELECT, locationMapper, username);
    }
}

package com.will.weather.repository.impl;

import com.will.weather.exception.LocationAlreadyExistsException;
import com.will.weather.model.Location;
import com.will.weather.repository.LocationRepository;
import com.will.weather.repository.mapper.LocationMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
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
    SELECT name, longitude, latitude
    FROM locations l
    JOIN users u ON l.user_id = u.id
    WHERE u.login = ?
""";
    private static final String SQL_DELETE =
"""
    DELETE FROM locations l
    USING users u
    WHERE l.user_id = u.id
      AND u.login = ?
      AND l.longitude = ?
      AND l.latitude = ?
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
            log.info(
                    "Location: [{}] already exists on user with id: [{}], ignoring save operation",
                    location.getName(),
                    location.getUserId());
            throw new LocationAlreadyExistsException(
                    String.format("Location [%s] is already added.", location.getName()));
        }
    }

    @Override
    public List<Location> findLocationsByUsername(String username) {
        return jdbcTemplate.query(SQL_SELECT, locationMapper, username);
    }

    @Override
    public void deleteLocationByLogin(String login, BigDecimal latitude, BigDecimal longitude) {
        int rows = jdbcTemplate.update(SQL_DELETE, login, longitude, latitude);
        String logMessage =
                rows >= 1
                        ? String.format("Deletion of the %s's location was successful", login)
                        : String.format("Nothing got deleted from user %s", login);
        log.info(logMessage);
    }
}

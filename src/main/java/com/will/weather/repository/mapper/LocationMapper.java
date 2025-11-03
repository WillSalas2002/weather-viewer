package com.will.weather.repository.mapper;

import com.will.weather.model.Location;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LocationMapper implements RowMapper<Location> {

    @Override
    public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Location.builder()
                .withName(rs.getString("name"))
                .withLongitude(rs.getBigDecimal("longitude"))
                .withLatitude(rs.getBigDecimal("latitude"))
                .build();
    }
}

package com.will.weather.repository;

import com.will.weather.model.Location;

import java.util.List;

public interface LocationRepository {

    void save(Location location);

    List<Location> findLocationsByUsername(String username);
}

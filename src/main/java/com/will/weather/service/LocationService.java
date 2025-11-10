package com.will.weather.service;

import com.will.weather.dto.AddLocationDto;
import com.will.weather.dto.RemoveLocationDto;

public interface LocationService {

    void save(AddLocationDto location, String login);

    void remove(RemoveLocationDto removeLocationDto);
}

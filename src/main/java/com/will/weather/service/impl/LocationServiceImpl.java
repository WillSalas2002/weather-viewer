package com.will.weather.service.impl;

import com.will.weather.dto.AddLocationDto;
import com.will.weather.dto.RemoveLocationDto;
import com.will.weather.exception.UserNotFoundException;
import com.will.weather.model.Location;
import com.will.weather.repository.LocationRepository;
import com.will.weather.repository.UserRepository;
import com.will.weather.service.LocationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    @Override
    @Transactional
    public void save(AddLocationDto addLocationDto, String login) {
        Long userId =
                userRepository
                        .findUserIdByLogin(login)
                        .orElseThrow(
                                () -> {
                                    log.warn("User [{}] not found", login);
                                    return new UserNotFoundException(
                                            String.format("No such user with login %s", login));
                                });
        log.info("Adding location [{}] to the user [{}]", addLocationDto.getName(), login);
        locationRepository.save(
                Location.builder()
                        .withUserId(userId)
                        .withName(addLocationDto.getName())
                        .withLatitude(addLocationDto.getLatitude())
                        .withLongitude(addLocationDto.getLongitude())
                        .build());
        log.info(
                "Successfully added location [{}] to the user [{}]",
                addLocationDto.getName(),
                login);
    }

    @Override
    public void remove(RemoveLocationDto removeLocationDto) {
        locationRepository.deleteLocationByLogin(
                removeLocationDto.getLogin(),
                removeLocationDto.getLatitude(),
                removeLocationDto.getLongitude());
    }
}

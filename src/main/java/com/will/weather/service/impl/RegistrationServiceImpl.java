package com.will.weather.service.impl;

import com.will.weather.model.Session;
import com.will.weather.model.User;
import com.will.weather.repository.SessionRepository;
import com.will.weather.repository.UserRepository;
import com.will.weather.service.RegistrationService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    @Override
    public UUID registerUser(String username, String password) {
        Long savedUserId = userRepository.save(new User(username, password));
        UUID sessionId = UUID.randomUUID();
        sessionRepository.save(
                new Session(sessionId, new User(savedUserId), LocalDateTime.now().plusHours(1)));
        return sessionId;
    }
}

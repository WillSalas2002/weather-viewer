package com.will.weather.service.impl;

import com.will.weather.model.Session;
import com.will.weather.model.User;
import com.will.weather.repository.SessionRepository;
import com.will.weather.repository.UserRepository;
import com.will.weather.service.RegistrationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    @Override
    @Transactional
    public UUID registerUser(String username, String password) {
        log.info("Saving user [{}] with encoded password", username);
        Long savedUserId =
                userRepository.save(
                        User.builder()
                                .withLogin(username)
                                .withPassword(passwordEncoder.encode(password))
                                .build());

        log.info("Creating a new session for user [{}]", username);
        UUID sessionId = UUID.randomUUID();
        sessionRepository.save(
                new Session(sessionId, savedUserId, LocalDateTime.now().plusHours(1)));
        log.info("User [{}] successfully registered in the system", username);
        return sessionId;
    }
}

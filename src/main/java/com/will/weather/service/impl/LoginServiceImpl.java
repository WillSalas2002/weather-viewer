package com.will.weather.service.impl;

import com.will.weather.dto.LoginDto;
import com.will.weather.exception.InvalidCredentialsException;
import com.will.weather.exception.UserNotFoundException;
import com.will.weather.model.Session;
import com.will.weather.model.User;
import com.will.weather.repository.SessionRepository;
import com.will.weather.repository.UserRepository;
import com.will.weather.service.LoginService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SessionRepository sessionRepository;

    @Override
    public boolean isSessionExpired(String sessionId) {
        LocalDateTime expirationDate = sessionRepository.findExpirationDateBySessionId(sessionId);
        return expirationDate.isBefore(LocalDateTime.now());
    }

    @Override
    public UUID login(LoginDto loginDto) {
        User user =
                userRepository
                        .findByLogin(loginDto.getLogin())
                        .orElseThrow(
                                () -> {
                                    log.warn(
                                            "User with login [{}] not found.", loginDto.getLogin());
                                    return new UserNotFoundException("User not found.");
                                });
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            log.warn("User [{}] entered incorrect password.", user.getLogin());
            throw new InvalidCredentialsException("Incorrect password.");
        }

        log.info(
                "User [{}] entered correct credentials, now creating a new session for him.",
                user.getLogin());
        UUID sessionId = UUID.randomUUID();
        sessionRepository.save(
                new Session(sessionId, user.getId(), LocalDateTime.now().plusHours(1)));
        log.info("User [{}] has successfully logged in", user.getLogin());

        return sessionId;
    }
}

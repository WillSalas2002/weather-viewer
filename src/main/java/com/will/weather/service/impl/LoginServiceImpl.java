package com.will.weather.service.impl;

import com.will.weather.dto.LoginDto;
import com.will.weather.exception.InvalidCredentialsException;
import com.will.weather.model.Session;
import com.will.weather.model.User;
import com.will.weather.repository.SessionRepository;
import com.will.weather.repository.UserRepository;
import com.will.weather.service.LoginService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    @Override
    public boolean checkCredentials(LoginDto loginDto) {
        Optional<User> userOptional =
                userRepository.findByUsernameAndPassword(
                        loginDto.getUsername(), loginDto.getPassword());
        return userOptional.isPresent();
    }

    @Override
    public boolean isSessionExpired(String sessionId) {
        LocalDateTime expirationDate = sessionRepository.findExpirationDateBySessionId(sessionId);
        return expirationDate.isBefore(LocalDateTime.now());
    }

    @Override
    public UUID login(LoginDto loginDto) {
        UUID sessionId = UUID.randomUUID();
        User user =
                userRepository
                        .findByUsernameAndPassword(loginDto.getUsername(), loginDto.getPassword())
                        .orElseThrow(
                                () ->
                                        new InvalidCredentialsException(
                                                "Invalid username or password."));
        sessionRepository.save(new Session(sessionId, user, LocalDateTime.now().plusHours(1)));
        return sessionId;
    }
}

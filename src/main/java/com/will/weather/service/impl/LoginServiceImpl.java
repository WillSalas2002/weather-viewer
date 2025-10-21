package com.will.weather.service.impl;

import com.will.weather.dto.LoginDto;
import com.will.weather.model.User;
import com.will.weather.repository.SessionRepository;
import com.will.weather.repository.UserRepository;
import com.will.weather.service.LoginService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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
}

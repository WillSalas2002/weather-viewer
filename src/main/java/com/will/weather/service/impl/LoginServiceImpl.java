package com.will.weather.service.impl;

import com.will.weather.dto.LoginDto;
import com.will.weather.model.User;
import com.will.weather.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl {
    private final UserRepository userRepository;

    public boolean checkCredentials(LoginDto loginDto) {
        Optional<User> userOptional = userRepository.findByUsernameAndPassword(
                loginDto.getUsername(), loginDto.getPassword());
        return userOptional.isPresent();
    }
}

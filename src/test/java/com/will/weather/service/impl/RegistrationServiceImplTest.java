package com.will.weather.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.will.weather.config.AppConfig;
import com.will.weather.exception.UserAlreadyExistsException;
import com.will.weather.model.User;
import com.will.weather.repository.UserRepository;
import com.will.weather.service.LoginService;
import com.will.weather.service.RegistrationService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.Optional;
import java.util.UUID;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
class RegistrationServiceImplTest {

    private String login;
    private String password;

    @Autowired private RegistrationService registrationService;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private LoginService loginService;
    @Autowired private UserRepository userRepository;
    @Autowired private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        login = "Will";
        password = "password";
    }

    @AfterEach
    void clear() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "sessions", "users", "locations");
    }

    @Test
    void whenUserRegistersSessionIsCreatedAutomatically() {
        registrationService.registerUser(login, password);
        Optional<User> userOptional = userRepository.findByLogin(login);

        int sessionRowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "sessions");
        assertTrue(userOptional.isPresent());
        assertNotNull(userOptional.get().getId());
        assertEquals(login, userOptional.get().getLogin());
        assertTrue(passwordEncoder.matches(password, userOptional.get().getPassword()));

        assertEquals(1, sessionRowCount);
    }

    @Test
    void whenLoginIsTakenThenThrowException() {
        registrationService.registerUser(login, password);

        assertThrows(UserAlreadyExistsException.class, () -> registrationService.registerUser(login, password));
    }

    @Test
    void sessionExpirationAfterTTLEnds() throws InterruptedException {
        UUID sessionId = registrationService.registerUser(login, password);

        Thread.sleep(3100);

        assertTrue(loginService.isSessionExpired(sessionId.toString()));
    }
}

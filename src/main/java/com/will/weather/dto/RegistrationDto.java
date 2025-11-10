package com.will.weather.dto;

import com.will.weather.validation.PasswordMatches;

import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatches
public class RegistrationDto {

    @Size(min = 3, max = 20, message = "Username should be between 3 and 20 characters long")
    private String username;

    @Size(min = 5, max = 20, message = "Password should be between 5 and 20 characters long")
    private String password;

    @Size(min = 5, max = 20, message = "Password should be between 5 and 20 characters long")
    private String repeatPassword;
}

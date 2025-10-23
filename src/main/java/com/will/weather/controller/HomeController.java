package com.will.weather.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/weather")
@RequiredArgsConstructor
public class HomeController {

    @GetMapping
    public String getHomePage() {
        return "index";
    }
}

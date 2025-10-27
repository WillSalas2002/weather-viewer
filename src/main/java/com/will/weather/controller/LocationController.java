package com.will.weather.controller;

import com.will.weather.client.dto.LocationDto;
import com.will.weather.service.LocationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping
    public String getLocations(@RequestParam(name = "name") String name, Model model) {
        List<LocationDto> locations = locationService.getAll(name);
        model.addAttribute("locations", locations);
        return "search-results";
    }
}

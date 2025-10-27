package com.will.weather.controller;

import com.will.weather.client.dto.LocationResponse;
import com.will.weather.dto.LocationDto;
import com.will.weather.service.LocationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping
    public String getLocations(@RequestParam(name = "name") String name, Model model) {
        List<LocationResponse> locations = locationService.getAll(name);
        model.addAttribute("locations", locations);
        model.addAttribute("location", new LocationDto());
        return "search-results";
    }

    @PostMapping("/add")
    public String add(LocationDto location) {
        return "redirect:/weather";
    }
}

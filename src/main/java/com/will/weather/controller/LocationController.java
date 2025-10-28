package com.will.weather.controller;

import com.will.weather.client.dto.LocationResponse;
import com.will.weather.constants.ApiPaths;
import com.will.weather.constants.HtmlPages;
import com.will.weather.dto.LocationDto;
import com.will.weather.service.LocationService;

import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping(ApiPaths.LOCATION)
@RequiredArgsConstructor
public class LocationController extends BaseController {

    private final LocationService locationService;

    @GetMapping
    public String getLocations(@RequestParam(name = "name") String name, Model model) {
        List<LocationResponse> locations = locationService.getAll(name);
        model.addAttribute("locations", locations);
        model.addAttribute("location", new LocationDto());
        return HtmlPages.SEARCH_RESULTS;
    }

    @PostMapping(ApiPaths.ADD_LOCATION)
    public String add(HttpServletRequest request, LocationDto location) {
        Optional<String> sessionIdOptional = readCookie(request);
        locationService.save(location, sessionIdOptional.get());
        return "redirect:" + ApiPaths.HOME;
    }
}

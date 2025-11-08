package com.will.weather.controller;

import com.will.weather.client.dto.LocationResponse;
import com.will.weather.constants.AppConstants;
import com.will.weather.dto.AddLocationDto;
import com.will.weather.dto.RemoveLocationDto;
import com.will.weather.service.LocationService;

import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequestMapping(AppConstants.LOCATION_PATH)
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping
    public String getLocations(
            @RequestParam(name = "name") String name, HttpServletRequest request, Model model) {
        String login = (String) request.getSession().getAttribute(AppConstants.SESSION_NAME);
        log.info("User [{}] is searching for location [{}]", login, name);
        List<LocationResponse> locations = locationService.getAll(name);

        model.addAttribute("locations", locations);
        model.addAttribute("addLocationDto", new AddLocationDto());
        model.addAttribute("removeLocationDto", new RemoveLocationDto());
        model.addAttribute(AppConstants.SESSION_NAME, login);

        return AppConstants.SEARCH_RESULTS_PAGE;
    }

    @PostMapping
    public String add(HttpServletRequest request, AddLocationDto location) {
        String login = (String) request.getSession().getAttribute(AppConstants.SESSION_NAME);
        log.info("User [{}] is trying to add location [{}]", login, location.getName());
        locationService.save(location, login);
        return "redirect:" + AppConstants.HOME_PATH;
    }

    @DeleteMapping
    public String remove(RemoveLocationDto removeLocationDto) {
        locationService.remove(removeLocationDto);
        return "redirect:" + AppConstants.HOME_PATH;
    }
}

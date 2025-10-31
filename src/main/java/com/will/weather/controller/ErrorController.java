package com.will.weather.controller;

import com.will.weather.constants.AppConstants;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(AppConstants.ERROR_PATH)
public class ErrorController {

    @GetMapping
    public String error(Model model) {
        model.addAttribute("homeUrl", AppConstants.HOME_PATH);
        return AppConstants.ERROR_PAGE;
    }
}

package com.will.weather.controller;

import com.will.weather.constants.AppConstants;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(AppConstants.ERROR_PATH)
public class ErrorController {

    public String error() {
        return AppConstants.ERROR_PAGE;
    }
}

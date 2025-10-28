package com.will.weather.controller;

import com.will.weather.constants.ApiPaths;
import com.will.weather.constants.HtmlPages;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.unbescape.html.HtmlEscape;

@Controller
@RequestMapping(ApiPaths.ERROR)
public class ErrorController extends BaseController {

    public String error() {
        return HtmlPages.ERROR;
    }
}

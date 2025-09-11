package com.will.weather.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {AppConfig.class, WebMVCConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
}

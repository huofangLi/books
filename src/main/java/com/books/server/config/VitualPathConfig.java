package com.books.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class VitualPathConfig implements WebMvcConfigurer {
    @Autowired
    private ApplicationProperties applicationProper;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/**")
            .addResourceLocations("file:" + applicationProper.getFileLocal().getPath());
    }
}

package com.example.foodsocialproject.controller.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path routeUploadDir1 = Paths.get("./post-images");
        String postUploadPath = routeUploadDir1.toFile().getAbsolutePath();
        registry.addResourceHandler("/post-images/**").addResourceLocations("file:/"+postUploadPath+"/");
    }
}

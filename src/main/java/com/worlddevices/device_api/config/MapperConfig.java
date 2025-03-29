package com.worlddevices.device_api.config;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for the ModelMapper bean.
 *
 * ModelMapper is a library that simplifies object mapping between different layers of the application.
 */
@Slf4j
@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        try {
            ModelMapper modelMapper = new ModelMapper();
            log.info("ModelMapper bean created successfully.");
            return modelMapper;
        } catch (Exception e) {
            log.error("Error creating ModelMapper bean: {}", e.getMessage(), e);
            throw e;
        }
    }

}

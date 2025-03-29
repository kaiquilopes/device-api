package com.worlddevices.device_api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * Configuration class for the RestTemplate bean.
 * <p>
 * RestTemplate is a tool for making HTTP requests in a simplified way.
 * This configuration adds support for JSON message conversion using Jackson.
 */
@Slf4j
@Configuration
public class RestTemplateConfig {

    /**
     * Creates and configures a RestTemplate bean.
     *
     * @return a configured instance of RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        try {
            RestTemplate restTemplate = new RestTemplate();

            // Configure the message converter to support JSON
            MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
            mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(new MediaType("application", "json")));
            restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);

            log.info("RestTemplate bean created successfully with MappingJackson2HttpMessageConverter.");
            return restTemplate;
        } catch (Exception e) {
            log.error("Error creating RestTemplate bean: {}", e.getMessage(), e);
            throw e;
        }
    }

}

package com.worlddevices.device_api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Device API", version = "1.0", description = "API for managing devices"))
public class SwaggerConfig {
}

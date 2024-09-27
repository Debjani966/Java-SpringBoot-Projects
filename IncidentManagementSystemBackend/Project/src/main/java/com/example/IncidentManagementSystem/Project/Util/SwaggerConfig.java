package com.example.IncidentManagementSystem.Project.Util;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI imaOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Incident Management System")
                        .version("1.0")
                        .description("API for the Incident Management System application"));
    }
}

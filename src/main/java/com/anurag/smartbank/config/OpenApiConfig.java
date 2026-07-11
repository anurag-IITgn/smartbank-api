package com.anurag.smartbank.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI smartBankOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("SmartBank Backend API")
                        .description("Production-style Banking Backend built using Spring Boot, Spring Security, JPA, Hibernate, MySQL and Docker.")
                        .version("Version 1.0")
                        .contact(new Contact()
                                .name("Anurag")
                                .url("https://github.com/anurag-IITgn"))
                        .license(new License()
                                .name("MIT License")))
                .externalDocs(new ExternalDocumentation()
                        .description("SmartBank GitHub Repository"));
    }
}
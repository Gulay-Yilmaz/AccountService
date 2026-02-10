package com.example.accountservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Account & Wallet Service API")
                        .version("1.0")
                        .description("Enterprise-grade Digital Wallet Backend for secure money transfers and account management.")
                        .contact(new Contact()
                                .name("Gulay Yilmaz")
                                .email("gulayyilmaz4158@gmail.com")));
    }
}
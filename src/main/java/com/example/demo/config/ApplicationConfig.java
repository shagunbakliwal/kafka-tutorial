package com.example.demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfig {
    @Bean
    @Qualifier("my-downstream-service-rest-template")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

package com.example.search.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Value("${api.username}")
    private String username;

    @Value("${api.password}")
    private String password;

    @Bean
    @LoadBalanced // Enables Ribbon load balancing
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .basicAuthentication(username, password) // Apply basic authentication
                .build(); // Build and return RestTemplate with load balancing
    }
}

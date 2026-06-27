package com.neeraj.assistant.ai.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class GroqConfig {

    @Value("${groq.api.url}")
    private String groqApiUrl;

    @Value("${groq.model}")
    private String model;

    @Bean
    public RestClient groqRestClient() {
        return RestClient.builder()
                .baseUrl(groqApiUrl)
                .build();
    }

    @Bean
    public String groqModel() {
        return model;
    }
}
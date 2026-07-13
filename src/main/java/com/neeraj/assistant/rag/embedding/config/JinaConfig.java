package com.neeraj.assistant.rag.embedding.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class JinaConfig {
    
    @Value("${jina.api.url}")
    private String jinaApiUrl;
    
    @Value("${jina.api.model}")
    private String model;
    
    @Bean
    public RestClient jinaRestClient(RestClient.Builder builder){
        return builder
                 .baseUrl(jinaApiUrl)
                 .build();
    }

    @Bean
    public String JinaModel() {
        return model;
    }

}

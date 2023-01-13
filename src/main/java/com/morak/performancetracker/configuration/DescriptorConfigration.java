package com.morak.performancetracker.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DescriptorConfigration {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}

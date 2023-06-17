package com.morak.performancetracker.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DescriptorConfigration {
    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper()
    }
}

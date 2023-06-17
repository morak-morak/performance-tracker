package com.morak.performancetracker.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DescriptorConfiguration {
    @Bean
    fun objectMapper(): ObjectMapper {
        return jacksonObjectMapper()
    }
}

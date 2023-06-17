package com.morak.performancetracker.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "com.morak.performance-tracker.logs")
data class DescriptorProperties(
    val path: String = "./logs/",
    val file: String = "performance"
)

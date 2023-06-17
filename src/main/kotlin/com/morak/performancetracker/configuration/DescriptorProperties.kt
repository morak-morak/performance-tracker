package com.morak.performancetracker.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.bind.DefaultValue

@ConstructorBinding
@ConfigurationProperties(prefix = "com.morak.performance-tracker.logs")
class DescriptorProperties(
    @param:DefaultValue("./logs/") val path: String,
    @param:DefaultValue("performance") val file: String
)

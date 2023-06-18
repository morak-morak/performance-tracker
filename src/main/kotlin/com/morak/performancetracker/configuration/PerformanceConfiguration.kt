package com.morak.performancetracker.configuration

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy

@Configuration
@ComponentScan(basePackages = ["com.morak.performancetracker"])
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableConfigurationProperties(value = [DescriptorProperties::class])
class PerformanceConfiguration

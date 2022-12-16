package com.morak.performancetracker.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "com.morak.performancetracker.aop")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class PerformanceConfiguration {
}

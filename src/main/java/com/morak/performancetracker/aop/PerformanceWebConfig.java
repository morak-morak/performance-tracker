package com.morak.performancetracker.aop;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// todo : web 쪽 처리할때 확인
@Configuration
public class PerformanceWebConfig implements WebMvcConfigurer {

    private final PerformanceMonitor performanceMonitor;

    public PerformanceWebConfig(PerformanceMonitor performanceMonitor) {
        this.performanceMonitor = performanceMonitor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PerformanceInterceptor(performanceMonitor));
    }
}

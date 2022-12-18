package com.morak.performancetracker.aop.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final WebMonitor webMonitor;

    public WebConfiguration(WebMonitor webMonitor) {
        this.webMonitor = webMonitor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PerformanceInterceptor(webMonitor));
    }
}

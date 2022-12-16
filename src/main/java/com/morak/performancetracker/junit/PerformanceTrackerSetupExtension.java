package com.morak.performancetracker.junit;

import com.morak.performancetracker.aop.PerformanceMonitorAop;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class PerformanceTrackerSetupExtension implements BeforeEachCallback {
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        ApplicationContext applicationContext = SpringExtension.getApplicationContext(context);
        PerformanceMonitorAop monitor = applicationContext.getBean(PerformanceMonitorAop.class);
        monitor.setFlag();
    }
}

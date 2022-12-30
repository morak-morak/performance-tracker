package com.morak.performancetracker.junit;

import com.morak.performancetracker.Monitor;
import com.morak.performancetracker.context.MethodContextManager;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class PerformanceTrackerSetupExtension implements AfterEachCallback, AfterAllCallback {

    private final Logger log = LoggerFactory.getLogger("PERFORMANCE");

    @Override
    public void afterEach(ExtensionContext context) {
        ApplicationContext applicationContext = SpringExtension.getApplicationContext(context);
        if (context.getExecutionException().isPresent()) {
            log.warn("fails on test execution" + context.getDisplayName());
            return;
        }
        MethodContextManager manager = applicationContext.getBean(MethodContextManager.class);
        manager.afterEach(applicationContext.getBeansOfType(Monitor.class).values());
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        ApplicationContext applicationContext = SpringExtension.getApplicationContext(context);
        if (context.getExecutionException().isPresent()) {
            log.warn("fails on test execution" + context.getDisplayName());
            return;
        }
        MethodContextManager manager = applicationContext.getBean(MethodContextManager.class);
        manager.afterAll(applicationContext.getBeansOfType(Monitor.class).values());
    }
}

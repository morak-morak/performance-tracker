package com.morak.performancetracker.junit;

import com.morak.performancetracker.Monitor;
import com.morak.performancetracker.description.Descriptor;
import java.util.Map;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class PerformanceTrackerSetupExtension implements AfterEachCallback {

    private final Logger log = LoggerFactory.getLogger("PERFORMANCE");

    @Override
    public void afterEach(ExtensionContext context) {
        ApplicationContext applicationContext = SpringExtension.getApplicationContext(context);
        if (context.getExecutionException().isPresent()) {
            log.warn("fails on test execution");
            return;
        }
        Descriptor descriptor = applicationContext.getBean(Descriptor.class);
        Map<String, Monitor> beansOfType = applicationContext.getBeansOfType(Monitor.class);
        for (Monitor monitor : beansOfType.values()) {
            if (monitor.getResult() == null) {
                continue;
            }
            descriptor.describe(monitor.getResult());
            monitor.clear();
        }
    }
}

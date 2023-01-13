package com.morak.performancetracker.junit;

import com.morak.performancetracker.context.Accumulator;
import com.morak.performancetracker.context.ContextManager;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class PerformanceTrackerSetupExtension implements AfterEachCallback, AfterAllCallback {

    @Override
    public void afterEach(ExtensionContext context) {
        if (context.getExecutionException().isPresent()) {
            return;
        }
        ApplicationContext applicationContext = getApplicationContext(context);
        applicationContext.getBeansOfType(ContextManager.class)
                .values()
                .forEach(manager -> manager.afterEach(
                        applicationContext.getBean(Accumulator.class),
                        context.getRequiredTestClass().getName()
                ));
    }

    @Override
    public void afterAll(ExtensionContext context) {
        ApplicationContext applicationContext = getApplicationContext(context);
        applicationContext.getBeansOfType(ContextManager.class)
                .values()
                .forEach(manager -> manager.afterClass(
                        applicationContext.getBean(Accumulator.class),
                        context.getRequiredTestClass().getName()
                ));
    }

    private ApplicationContext getApplicationContext(ExtensionContext context) {
        return SpringExtension.getApplicationContext(context);
    }
}

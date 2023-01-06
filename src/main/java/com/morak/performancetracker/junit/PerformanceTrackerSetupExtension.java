package com.morak.performancetracker.junit;

import com.morak.performancetracker.PerformanceTracker;
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
        ApplicationContext applicationContext = getApplicationContext(context);
        if (context.getExecutionException().isPresent()) {
            return;
        }
        ContextManager manager = applicationContext.getBean(determineContextManager(context));
        manager.afterEach(applicationContext.getBean(Accumulator.class), context.getRequiredTestMethod().getName());
    }

    @Override
    public void afterAll(ExtensionContext context) {
        ApplicationContext applicationContext = getApplicationContext(context);
        ContextManager manager = applicationContext.getBean(determineContextManager(context));
        manager.afterClass(applicationContext.getBean(Accumulator.class), context.getRequiredTestClass().getName());
    }

    private Class<? extends ContextManager> determineContextManager(ExtensionContext context) {
        PerformanceTracker annotation = context.getRequiredTestClass().getAnnotation(PerformanceTracker.class);
        return annotation.context().getContextManagerClass();
    }

    private ApplicationContext getApplicationContext(ExtensionContext context) {
        return SpringExtension.getApplicationContext(context);
    }
}

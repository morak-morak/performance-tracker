package com.morak.performancetracker.junit;

import com.morak.performancetracker.PerformanceTracker;
import com.morak.performancetracker.context.Accumulator;
import com.morak.performancetracker.context.ContextManager;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class PerformanceTrackerSetupExtension implements BeforeEachCallback, AfterEachCallback, AfterAllCallback {

    @Override
    public void beforeEach(final ExtensionContext context) {
        ApplicationContext applicationContext = getApplicationContext(context);
        Accumulator accumulator = applicationContext.getBean(Accumulator.class);
        accumulator.setMethodName(context.getRequiredTestMethod().getName());
    }

    @Override
    public void afterEach(ExtensionContext context) {
        ApplicationContext applicationContext = getApplicationContext(context);
        if (context.getExecutionException().isPresent()) {
            return;
        }
        ContextManager manager = applicationContext.getBean(determineContextManager(context));
        manager.afterEach(applicationContext.getBean(Accumulator.class));
    }

    @Override
    public void afterAll(ExtensionContext context) {
        ApplicationContext applicationContext = SpringExtension.getApplicationContext(context);
        ContextManager manager = applicationContext.getBean(determineContextManager(context));
        manager.afterClass(context.getRequiredTestClass().getName());
    }

    private Class<? extends ContextManager> determineContextManager(ExtensionContext context) {
        PerformanceTracker annotation = context.getRequiredTestClass().getAnnotation(PerformanceTracker.class);
        return annotation.context().getContextManagerClass();
    }

    private ApplicationContext getApplicationContext(final ExtensionContext context) {
        return SpringExtension.getApplicationContext(context);
    }
}

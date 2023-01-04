package com.morak.performancetracker.junit;

import com.morak.performancetracker.PerformanceTracker;
import com.morak.performancetracker.context.Accumulator;
import com.morak.performancetracker.context.ContextManager;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store.CloseableResource;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class PerformanceTrackerAllTestsExtension implements BeforeAllCallback, CloseableResource {

    private static final String STORE_KEY = "performance-tracker";

    private static boolean started = false;
    private static ApplicationContext applicationContext;
    private static ContextManager contextManager;

    @Override
    public void beforeAll(ExtensionContext context) {
        synchronized (this) {
            if (!started) {
                started = true;
                applicationContext = SpringExtension.getApplicationContext(context);
                contextManager = applicationContext.getBean(determineContextManager(context));
                context.getRoot().getStore(Namespace.GLOBAL).put(STORE_KEY, this);
            }
        }
    }

    @Override
    public void close() {
        contextManager.afterAll(applicationContext.getBean(Accumulator.class));
    }

    private static Class<? extends ContextManager> determineContextManager(ExtensionContext context) {
        PerformanceTracker annotation = context.getRequiredTestClass().getAnnotation(PerformanceTracker.class);
        return annotation.context().getContextManagerClass();
    }
}

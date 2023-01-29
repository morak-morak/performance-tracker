package com.morak.performancetracker.junit;

import com.morak.performancetracker.PerformanceTracker;
import com.morak.performancetracker.context.Accumulator;
import com.morak.performancetracker.context.ContextManager;
import com.morak.performancetracker.context.TestMetadata;
import org.junit.jupiter.api.extension.*;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class PerformanceTrackerSetupExtension implements BeforeAllCallback, BeforeEachCallback, AfterEachCallback, AfterAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        ApplicationContext applicationContext = getApplicationContext(context);
        ContextManager manager = applicationContext.getBean(determineContextManager(context));
        TestMetadata testMetadata = TestMetadata.of(context.getRequiredTestClass().getName());
        manager.beforeClass(testMetadata);
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        ApplicationContext applicationContext = getApplicationContext(context);
        ContextManager manager = applicationContext.getBean(determineContextManager(context));
        TestMetadata testMetadata = TestMetadata.of(context.getRequiredTestClass().getName(), context.getRequiredTestMethod().getName());
        manager.beforeEach(testMetadata);
    }

    @Override
    public void afterEach(ExtensionContext context) {
        ApplicationContext applicationContext = getApplicationContext(context);
        if (context.getExecutionException().isPresent()) {
            return;
        }
        ContextManager manager = applicationContext.getBean(determineContextManager(context));
        TestMetadata testMetadata = TestMetadata.of(context.getRequiredTestClass().getName(), context.getRequiredTestMethod().getName());
        manager.afterEach(applicationContext.getBean(Accumulator.class), testMetadata);
    }

    @Override
    public void afterAll(ExtensionContext context) {
        ApplicationContext applicationContext = getApplicationContext(context);
        ContextManager manager = applicationContext.getBean(determineContextManager(context));
        TestMetadata testMetadata = TestMetadata.of(context.getRequiredTestClass().getName());
        manager.afterClass(applicationContext.getBean(Accumulator.class), testMetadata);
    }

    private Class<? extends ContextManager> determineContextManager(ExtensionContext context) {
        PerformanceTracker annotation = context.getRequiredTestClass().getAnnotation(PerformanceTracker.class);
        return annotation.context().getContextManagerClass();
    }

    private ApplicationContext getApplicationContext(ExtensionContext context) {
        return SpringExtension.getApplicationContext(context);
    }
}

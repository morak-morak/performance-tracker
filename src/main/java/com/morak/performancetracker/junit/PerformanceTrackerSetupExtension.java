package com.morak.performancetracker.junit;

import com.morak.performancetracker.Monitor;
import com.morak.performancetracker.context.Accumulator;
import com.morak.performancetracker.context.MethodContextManager;
import java.lang.reflect.Method;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class PerformanceTrackerSetupExtension implements BeforeEachCallback, AfterEachCallback, AfterAllCallback {

    private final Logger log = LoggerFactory.getLogger("PERFORMANCE");

    @Override
    public void beforeEach(final ExtensionContext context) {
        Method method = context.getTestMethod().orElseThrow(IllegalStateException::new);
        ApplicationContext applicationContext = getApplicationContext(context);
        Accumulator accumulator = applicationContext.getBean(Accumulator.class);
        accumulator.setMethodName(method.getName());
    }

    @Override
    public void afterEach(ExtensionContext context) {
        ApplicationContext applicationContext = getApplicationContext(context);
        if (context.getExecutionException().isPresent()) {
            log.warn("fails on test execution" + context.getDisplayName());
            return;
        }
        MethodContextManager manager = applicationContext.getBean(MethodContextManager.class);
        manager.afterEach(applicationContext.getBean(Accumulator.class));
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        ApplicationContext applicationContext = getApplicationContext(context);
        if (context.getExecutionException().isPresent()) {
            log.warn("fails on test execution" + context.getDisplayName());
            return;
        }
        MethodContextManager manager = applicationContext.getBean(MethodContextManager.class);
        manager.afterEach(applicationContext.getBean(Accumulator.class));
    }

    private ApplicationContext getApplicationContext(final ExtensionContext context) {
        return SpringExtension.getApplicationContext(context);
    }
}

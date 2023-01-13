package com.morak.performancetracker.junit;

import com.morak.performancetracker.PerformanceTracker;
import com.morak.performancetracker.context.Accumulator;
import com.morak.performancetracker.context.ContextManager;
import java.util.ArrayList;
import java.util.List;
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
    private static List<ContextManager> contextManagers;

    @Override
    public void beforeAll(ExtensionContext context) {
        synchronized (this) {
            if (!started) {
                started = true;
                applicationContext = SpringExtension.getApplicationContext(context);
                contextManagers = new ArrayList<>(applicationContext.getBeansOfType(ContextManager.class).values());
                context.getRoot().getStore(Namespace.GLOBAL).put(STORE_KEY, this);
            }
        }
    }

    @Override
    public void close() {
        contextManagers
                .forEach(manager -> manager.afterAll(applicationContext.getBean(Accumulator.class)));
    }
}

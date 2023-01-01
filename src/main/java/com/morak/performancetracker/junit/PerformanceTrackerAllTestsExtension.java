package com.morak.performancetracker.junit;

import com.morak.performancetracker.context.Accumulator;
import com.morak.performancetracker.context.ContextManager;
import com.morak.performancetracker.context.EndpointContextManager;
import com.morak.performancetracker.context.MethodContextManager;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store.CloseableResource;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class PerformanceTrackerAllTestsExtension implements BeforeAllCallback, CloseableResource {

    private static boolean started = false;
    private static ApplicationContext applicationContext;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        synchronized (this) {
            if (!started) {
                started = true;
                applicationContext = SpringExtension.getApplicationContext(context);
                context.getRoot().getStore(Namespace.GLOBAL).put("performance-tracker", this);
            }
        }
    }

    @Override
    public void close() throws Throwable {
        EndpointContextManager manager = applicationContext.getBean(EndpointContextManager.class);
        manager.afterAll(applicationContext.getBean(Accumulator.class));
    }
}

package com.morak.performancetracker.context;

import com.morak.performancetracker.Monitor;
import com.morak.performancetracker.description.Descriptor;
import com.morak.performancetracker.result.Result;
import java.util.Collection;
import org.springframework.stereotype.Component;

@Component
public class MethodContextManager implements ContextManager {

    private final Descriptor descriptor;

    public MethodContextManager(Descriptor descriptor) {
        this.descriptor = descriptor;
    }

    public void afterEach(Collection<Monitor> monitors) {
        for (Monitor monitor : monitors) {
            Result result = Result.of(monitor);
            descriptor.describe(result);
            monitor.clear();
        }
    }

    @Override
    public void afterAll(Collection<Monitor> monitors) {
        // todo
    }
}

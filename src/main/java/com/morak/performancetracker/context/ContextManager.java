package com.morak.performancetracker.context;

import com.morak.performancetracker.Monitor;
import com.morak.performancetracker.description.Descriptor;
import com.morak.performancetracker.result.Result;
import java.util.Collection;
import org.springframework.stereotype.Component;

@Component
public class ContextManager {

    private final Descriptor descriptor;

    public ContextManager(Descriptor descriptor) {
        this.descriptor = descriptor;
    }

    public void manage(Collection<Monitor> monitors) {
        for (Monitor monitor : monitors) {
            Result result = Result.of(monitor);
            descriptor.describe(result);
        }
    }
}

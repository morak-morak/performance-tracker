package com.morak.performancetracker.context;

import com.morak.performancetracker.Monitor;
import com.morak.performancetracker.description.Descriptor;
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
            if (monitor.getResult() == null) {
                continue;
            }
            descriptor.describe(monitor.getResult());
            monitor.clear();
        }
    }
}

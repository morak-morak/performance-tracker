package com.morak.performancetracker.context;

import com.morak.performancetracker.description.Descriptor;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.stereotype.Component;

@Component
public class MethodContextManager implements ContextManager {

    private final Descriptor descriptor;

    public MethodContextManager(Descriptor descriptor) {
        this.descriptor = descriptor;
    }

    @Override
    public void afterEach(Accumulator accumulator) {
        Map<String, List<Result>> results = accumulator.getResults();
        for (Entry<String, List<Result>> entry : results.entrySet()) {
            descriptor.describe(accumulator.getMethodName());
            for (Result result : entry.getValue()) {
                descriptor.describe(result);
            }
        }
    }

    @Override
    public void afterAll(Accumulator accumulator) {
        // do nothing
    }
}

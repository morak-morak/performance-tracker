package com.morak.performancetracker.context;

import com.morak.performancetracker.description.Descriptor;
import com.morak.performancetracker.utils.ConditionalOnPropertyContains;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnPropertyContains(value = "com.morak.performance-tracker.context.type", containsValue = "method", matchIfEmpty = true)
public class MethodContextManager implements ContextManager {

    private final Root contexts;
    private List<Scope> scopes = new ArrayList<>();
    private final Descriptor descriptor;

    public MethodContextManager(Descriptor descriptor) {
        this.contexts = new Root(new ArrayList<>());
        this.descriptor = descriptor;
    }

    @Override
    public void afterEach(Accumulator accumulator, String testMethodName) {
        Map<String, List<Result>> results = accumulator.getResults();
        scopes.add(new Scope(testMethodName, flatResults(results)));
    }

    private List<Result> flatResults(Map<String, List<Result>> results) {
        return results.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public void afterClass(Accumulator accumulator, String testClassName) {
        contexts.add(new Context(testClassName, this.scopes));
        this.scopes = new ArrayList<>();
    }

    @Override
    public void afterAll(Accumulator accumulator) {
        descriptor.describe(contexts);
    }
}

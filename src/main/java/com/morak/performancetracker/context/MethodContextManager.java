package com.morak.performancetracker.context;

import com.morak.performancetracker.description.Descriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class MethodContextManager implements ContextManager {

    private final List<Context> contexts = new ArrayList<>();
    private List<Scope> scopes = new ArrayList<>();
    private final Descriptor descriptor;

    public MethodContextManager(Descriptor descriptor) {
        this.descriptor = descriptor;
    }

    @Override
    public void afterEach(Accumulator accumulator, String testMethodName) {
        Map<String, List<Result>> results = accumulator.getResults();
        scopes.add(new Scope(testMethodName, flatResults(results)));
        accumulator.clear();
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
        for (final Context context : contexts) {
            descriptor.describe(context);
        }
    }
}

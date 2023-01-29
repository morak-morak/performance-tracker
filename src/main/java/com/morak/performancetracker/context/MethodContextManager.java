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

    private final ResultComposite root;
    private final Descriptor descriptor;

    public MethodContextManager(Descriptor descriptor) {
        this.root = new ResultComposite(TestMetadata.ROOT, new ArrayList<>());
        this.descriptor = descriptor;
    }

    @Override
    public void beforeClass(TestMetadata testMetadata) {
        this.root.findAndAdd(new ResultComposite(testMetadata, new ArrayList<>()), testMetadata.parent());
    }

    @Override
    public void beforeEach(TestMetadata testMetadata) {
        this.root.findAndAdd(new ResultComposite(testMetadata, new ArrayList<>()), testMetadata.parent());
    }

    @Override
    public void afterEach(Accumulator accumulator, TestMetadata testMetadata) {
        Map<String, List<MonitorResult>> results = accumulator.getResults();
        List<MonitorResult> resultFiles = flatResults(results);
        for (MonitorResult dto : resultFiles) {
            root.findAndAdd(new ResultLeaf(dto.getName(), dto.getElapsed()), testMetadata);
        }
        accumulator.clear();
    }

    private List<MonitorResult> flatResults(Map<String, List<MonitorResult>> results) {
        return results.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public void afterAll(Accumulator accumulator) {
        descriptor.describe(root);
    }
}

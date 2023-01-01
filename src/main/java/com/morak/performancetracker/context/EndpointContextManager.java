package com.morak.performancetracker.context;

import com.morak.performancetracker.description.Descriptor;
import com.morak.performancetracker.result.Result;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class EndpointContextManager implements ContextManager {

    private final Accumulator accumulator;
    private final Descriptor descriptor;

    public EndpointContextManager(Accumulator accumulator, Descriptor descriptor) {
        this.accumulator = accumulator;
        this.descriptor = descriptor;
    }

    @Override
    public void afterEach(Accumulator accumulator) {
        // todo
    }

    @Override
    public void afterAll(Accumulator accumulator) {
        accumulator.getResults().forEach(this::describeSummary);
    }

    private void describeSummary(String scope, List<Result> results) {
        Map<String, DoubleSummaryStatistics> summary = summarize(results);
        for (Entry<String, DoubleSummaryStatistics> entry : summary.entrySet()) {
            Result result = new Result(entry.getKey(), entry.getValue().getAverage());
            descriptor.describe(result);
        }
    }

    private Map<String, DoubleSummaryStatistics> summarize(List<Result> results) {
        return results.stream()
                .collect(Collectors.groupingBy(
                        Result::getName,
                        Collectors.summarizingDouble(Result::getElapsed)
                ));
    }
}

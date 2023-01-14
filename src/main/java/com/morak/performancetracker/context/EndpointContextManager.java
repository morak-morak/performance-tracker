package com.morak.performancetracker.context;

import com.morak.performancetracker.description.Descriptor;
import com.morak.performancetracker.utils.ConditionalOnPropertyContains;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnPropertyContains(value = "com.morak.performance-tracker.context.type", containsValue = "endpoint", matchIfEmpty = true)
public class EndpointContextManager implements ContextManager {

    private final Descriptor descriptor;
    private List<Scope> scopes = new ArrayList<>();

    public EndpointContextManager(Descriptor descriptor) {
        this.descriptor = descriptor;
    }

    @Override
    public void afterEach(Accumulator accumulator, String testMethodName) {
        List<Scope> scopes = accumulator.getResults().entrySet()
                .stream()
                .map(it -> summarizePerScope(it.getKey(), it.getValue()))
                .collect(Collectors.toList());
        this.scopes.addAll(scopes);
    }

    @Override
    public void afterAll(Accumulator accumulator) {
        descriptor.describe(new Root(List.of(new Context("ROOT", this.scopes))));
    }

    private Scope summarizePerScope(String scopeName, List<Result> results) {
        Map<String, DoubleSummaryStatistics> summary = summarize(results);
        List<Result> summaries = toResult(summary);
        return new Scope(scopeName, summaries);
    }

    private Map<String, DoubleSummaryStatistics> summarize(List<Result> results) {
        return results.stream()
                .collect(Collectors.groupingBy(
                        Result::getName,
                        Collectors.summarizingDouble(Result::getElapsed)
                ));
    }

    private List<Result> toResult(Map<String, DoubleSummaryStatistics> summary) {
        return summary.entrySet().stream()
                .map(it -> new Result(it.getKey(), it.getValue().getAverage()))
                .collect(Collectors.toList());
    }
}

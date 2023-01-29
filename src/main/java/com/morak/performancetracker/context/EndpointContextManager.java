package com.morak.performancetracker.context;

import com.morak.performancetracker.description.Descriptor;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class EndpointContextManager implements ContextManager {

    private final Descriptor descriptor;

    public EndpointContextManager(Descriptor descriptor) {
        this.descriptor = descriptor;
    }

    @Override
    public void afterAll(Accumulator accumulator) {
        List<Result> scopes = accumulator.getResults().entrySet()
                .stream()
                .map(it -> summarizePerScope(it.getKey(), it.getValue()))
                .collect(Collectors.toList());
        ResultComposite resultComposite = new ResultComposite(TestMetadata.ROOT, scopes);
        descriptor.describe(resultComposite);
    }

    private Result summarizePerScope(String scopeName, List<MonitorResult> results) {
        Map<String, DoubleSummaryStatistics> summary = summarize(results);
        List<Result> summaries = toResult(summary);
        TestMetadata testMetadata = new TestMetadata(scopeName, "");
        return new ResultComposite(testMetadata, summaries);
    }

    private Map<String, DoubleSummaryStatistics> summarize(List<MonitorResult> results) {
        return results.stream()
                .collect(Collectors.groupingBy(
                        MonitorResult::getName,
                        Collectors.summarizingDouble(MonitorResult::getElapsed)
                ));
    }

    private List<Result> toResult(Map<String, DoubleSummaryStatistics> summary) {
        return summary.entrySet().stream()
                .map(it -> new ResultLeaf(it.getKey(), it.getValue().getAverage()))
                .collect(Collectors.toList());
    }
}

package com.morak.performancetracker.context;

import com.morak.performancetracker.Monitor;
import com.morak.performancetracker.result.Result;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class Accumulator {

    private final ResultMapper resultMapper;
    private final Map<String, List<Result>> results;

    public Accumulator(ResultMapper resultMapper) {
        this.resultMapper = resultMapper;
        this.results = new HashMap<>();
    }

    public void add(Monitor monitor) {
        String signature = monitor.getSignature();
        results.computeIfAbsent(signature, k -> new ArrayList<>());
        results.get(signature).add(resultMapper.mapMonitor(monitor));
    }

    public Map<String, List<Result>> getResults() {
        return results;
    }
}

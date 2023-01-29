package com.morak.performancetracker.context;

import com.morak.performancetracker.Monitor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class Accumulator {

    private final ResultMapper resultMapper;
    private Map<String, List<MonitorResult>> results;

    public Accumulator(ResultMapper resultMapper) {
        this.resultMapper = resultMapper;
        this.results = new HashMap<>();
    }

    public void add(Monitor monitor) {
        String signature = monitor.getSignature();
        results.computeIfAbsent(signature, k -> new ArrayList<>());
        results.get(signature).add(resultMapper.mapMonitor(monitor));
    }

    public Map<String, List<MonitorResult>> getResults() {
        return results;
    }

    public void clear() {
        this.results = new HashMap<>();
    }
}

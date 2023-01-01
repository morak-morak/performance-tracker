package com.morak.performancetracker.context;

import com.morak.performancetracker.Monitor;
import com.morak.performancetracker.aop.persistence.QueryMonitor;
import com.morak.performancetracker.result.Result;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EndpointContextManager implements ContextManager {

    private final Accumulator accumulator;
    private final ResultMapper resultMapper;
    private final Map<String, List<Result>> map = new HashMap<>();

    public EndpointContextManager(Accumulator accumulator, ResultMapper resultMapper) {
        this.accumulator = accumulator;
        this.resultMapper = resultMapper;
    }

    @Override
    public void afterEach(Collection<Monitor> monitors) {
        for (Monitor monitor : monitors) {
            Result result = resultMapper.mapMonitor(monitor);
            accumulator.accumulate(result);
        }
    }

    @Override
    public void afterAll(Collection<Monitor> monitors) {
        // todo
    }
}

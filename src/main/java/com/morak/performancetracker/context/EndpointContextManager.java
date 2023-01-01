package com.morak.performancetracker.context;

import com.morak.performancetracker.Monitor;
import java.util.Collection;
import org.springframework.stereotype.Component;

public class EndpointContextManager implements ContextManager {

    private final Accumulator accumulator;

    public EndpointContextManager(Accumulator accumulator) {
        this.accumulator = accumulator;
    }

    @Override
    public void afterEach(Accumulator accumulator) {
        // todo
    }

    @Override
    public void afterAll(Accumulator accumulator) {
        // todo
    }
}

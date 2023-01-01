package com.morak.performancetracker.context;

public interface ContextManager {

    void afterEach(Accumulator accumulator);

    void afterAll(Accumulator accumulator);
}

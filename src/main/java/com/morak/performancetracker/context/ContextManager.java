package com.morak.performancetracker.context;

public interface ContextManager {

    void afterEach(Accumulator accumulator);

    void afterClass(String testClassName);

    void afterAll(Accumulator accumulator);
}

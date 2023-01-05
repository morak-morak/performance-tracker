package com.morak.performancetracker.context;

public interface ContextManager {

    default void afterEach(Accumulator accumulator, String testMethodName) {}

    default void afterClass(Accumulator accumulator, String testClassName) {};

    void afterAll(Accumulator accumulator);
}

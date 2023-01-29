package com.morak.performancetracker.context;

public interface ContextManager {

    default void beforeClass(TestMetadata testMetadata) {
    }

    default void beforeEach(TestMetadata testMetadata) {
    }

    default void afterEach(Accumulator accumulator, TestMetadata testMetadata) {
    }

    default void afterClass(Accumulator accumulator, TestMetadata testMetadata) {
    }

    void afterAll(Accumulator accumulator);
}

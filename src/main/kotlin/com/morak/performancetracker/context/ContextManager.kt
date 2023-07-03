package com.morak.performancetracker.context

interface ContextManager {
    fun beforeClass(testMetadata: TestMetadata) {}
    fun beforeEach(testMetadata: TestMetadata) {}
    fun afterEach(accumulator: Accumulator, testMetadata: TestMetadata) {}
    fun afterClass(accumulator: Accumulator, testMetadata: TestMetadata) {}
    fun afterAll(accumulator: Accumulator)
}

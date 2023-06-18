package com.morak.performancetracker.context

interface ContextManager {
    fun afterEach(accumulator: Accumulator, testMethodName: String) {}
    fun afterClass(accumulator: Accumulator, testClassName: String) {}
    fun afterAll(accumulator: Accumulator)
}

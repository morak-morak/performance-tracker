package com.morak.performancetracker.context


interface Result {
    fun findAndAdd(result: Result, testMetadata: TestMetadata)
    val result: String
    val subResults: List<Result>
}

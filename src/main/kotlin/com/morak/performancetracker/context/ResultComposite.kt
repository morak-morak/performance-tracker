package com.morak.performancetracker.context

import java.util.function.Consumer


class ResultComposite(
    private val testMetadata: TestMetadata,
    private val _subResults: MutableList<Result>
) : Result {

    override val result: String
        get() = testMetadata.self()

    override val subResults: List<Result>
        get() = _subResults

    override fun findAndAdd(result: Result, testMetadata: TestMetadata) {
        if (this.testMetadata == testMetadata) {
            _subResults.add(result)
            return
        }

        subResults.forEach(Consumer { subResult: Result ->
            subResult.findAndAdd(
                result,
                testMetadata
            )
        })
    }

}

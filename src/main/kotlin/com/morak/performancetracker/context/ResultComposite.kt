package com.morak.performancetracker.context


class ResultComposite(
    private val testMetadata: TestMetadata,
    subResults: List<Result> = emptyList()
) : Result {
    private val _subResults: MutableList<Result> = subResults.toMutableList()
    override val subResults: List<Result>
        get() = _subResults.toList()

    override val result: String
        get() = testMetadata.self

    override fun findAndAdd(result: Result, testMetadata: TestMetadata) {
        if (this.testMetadata == testMetadata) {
            _subResults.add(result)
            return
        }

        subResults.forEach { it.findAndAdd(result, testMetadata) }
    }
}

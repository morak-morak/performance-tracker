package com.morak.performancetracker.context


class ResultLeaf(
    private val name: String,
    private val elapsed: Double
) : Result {

    override fun findAndAdd(result: Result, testMetadata: TestMetadata) {}

    override val result: String
        get() = toString()
    override val subResults: List<Result>
        get() = emptyList()

    override fun toString(): String {
        return "name='" + name + '\'' +
                ", elapsed=" + elapsed
    }
}

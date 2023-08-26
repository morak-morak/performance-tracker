package com.morak.performancetracker.context

import com.morak.performancetracker.ContextType
import com.morak.performancetracker.description.Descriptor
import com.morak.performancetracker.utils.ConditionalOnPropertyContains
import com.morak.performancetracker.utils.average
import org.springframework.stereotype.Component


@Component
@ConditionalOnPropertyContains(
    value = "com.morak.performance-tracker.context.type",
    containsValue = "endpoint",
    matchIfEmpty = true
)
class EndpointContextManager(
    private val descriptor: Descriptor,
    results: List<Result>
) : ContextManager {
    private val _results: MutableList<Result> = results.toMutableList()
    val results: List<Result>
        get() = _results

    override fun afterEach(accumulator: Accumulator, testMetadata: TestMetadata) {
        val scopes = accumulator.results.entries
            .map { summarizePerScope(it.key, it.value) }
            .toList()
        _results.addAll(scopes)
    }

    override fun afterAll(accumulator: Accumulator) {
        val resultComposite = ResultComposite(TestMetadata.ROOT, results)
        descriptor.describe(resultComposite, ContextType.ENDPOINT)
    }

    private fun summarizePerScope(scopeName: String, results: List<MonitorResult>): Result {
        val summaries = summarize(results)
        val testMetadata = TestMetadata(scopeName)
        return ResultComposite(testMetadata, summaries);
    }

    private fun summarize(results: List<MonitorResult>): List<Result> {
        return results.groupBy { it.name }
            .map { (key, value) -> ResultLeaf(key, value.average { it.elapsed }) }
            .toList()
    }
}

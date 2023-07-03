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
    private val results: MutableList<Result>
) : ContextManager {

    override fun afterEach(accumulator: Accumulator, testMetadata: TestMetadata) {
        val scopes = accumulator.results.entries
            .map { (key, value) -> summarizePerScope(key, value) }
            .toMutableList()
        results.addAll(scopes)
    }

    override fun afterAll(accumulator: Accumulator) {
//        val scopes = accumulator.results.entries
//            .map { (key, value) -> summarizePerScope(key, value) }
//            .toMutableList()
        val resultComposite = ResultComposite(TestMetadata.ROOT, results)
        descriptor.describe(resultComposite, ContextType.ENDPOINT)
    }

    private fun summarizePerScope(scopeName: String, results: List<MonitorResult>): Result {
        val summary = summarize(results)
        val summaries = toResult(summary)
        val testMetadata = TestMetadata(scopeName)
        return ResultComposite(testMetadata, summaries);
    }

    private fun summarize(results: List<MonitorResult>): Map<String, Double> {
        return results.groupBy { it.name }
            .map { (key, value) -> key to value.average { it.elapsed } }
            .toMap()
    }

    private fun toResult(summary: Map<String, Double>): MutableList<Result> {
        return summary.map() { (key, value) ->
            ResultLeaf(key, value)
        }
            .toMutableList()
    }
}

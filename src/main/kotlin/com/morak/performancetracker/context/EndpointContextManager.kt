package com.morak.performancetracker.context

import com.morak.performancetracker.ContextType
import com.morak.performancetracker.description.Descriptor
import com.morak.performancetracker.utils.ConditionalOnPropertyContains
import com.morak.performancetracker.utils.average
import org.springframework.stereotype.Component


@Component
@ConditionalOnPropertyContains(value = "com.morak.performance-tracker.context.type", containsValue = "endpoint", matchIfEmpty = true)
class EndpointContextManager(
    private val descriptor: Descriptor,
    private val scopes: MutableList<Scope>,
) : ContextManager {
    override fun afterAll(accumulator: Accumulator) {
        descriptor.describe(Root(listOf(Context("ROOT", scopes))), ContextType.ENDPOINT)
    }

    override fun afterEach(accumulator: Accumulator, testMethodName: String) {
        val scopes = accumulator.results.entries
            .map { (key, value) -> summarizePerScope(key, value) }
        this.scopes.addAll(scopes)
    }

    private fun summarizePerScope(scopeName: String, results: List<Result>): Scope {
        val summary = summarize(results)
        val summaries = toResult(summary)
        return Scope(scopeName, summaries)
    }

    private fun summarize(results: List<Result>): Map<String, Double> {
        return results.groupBy { it.name }
            .map { (key, value) -> key to value.average { it.elapsed } }
            .toMap()
    }

    private fun toResult(summary: Map<String, Double>): List<Result> {
        return summary.map { (key, value) ->
            Result(key, value)
        }
    }
}

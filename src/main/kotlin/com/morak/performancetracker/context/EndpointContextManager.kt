package com.morak.performancetracker.context

import com.morak.performancetracker.description.Descriptor
import org.springframework.stereotype.Component


@Component
class EndpointContextManager(private val descriptor: Descriptor) : ContextManager {
    override fun afterAll(accumulator: Accumulator) {
        val scopes = accumulator.results.entries
            .map { (key, value): Map.Entry<String, List<Result>> ->
                summarizePerScope(
                    key, value
                )
            }
        descriptor.describe(Root(java.util.List.of(Context(scopes))))
    }

    private fun summarizePerScope(scopeName: String, results: List<Result>): Scope {
        val summary = summarize(results)
        val summaries = toResult(summary)
        return Scope(scopeName, summaries)
    }

    private fun summarize(results: List<Result>): Map<String, Double> {
        return results.groupBy { it.name }
            .map { (key, value) -> key to (value.sumOf { it.elapsed }) / value.size }
            .toMap()
    }

    private fun toResult(summary: Map<String, Double>): List<Result> {
        return summary.map { (key, value) ->
            Result(key, value)
        }
    }
}

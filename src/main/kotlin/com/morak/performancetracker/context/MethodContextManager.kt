package com.morak.performancetracker.context

import com.morak.performancetracker.ContextType
import com.morak.performancetracker.description.Descriptor
import com.morak.performancetracker.utils.ConditionalOnPropertyContains
import org.springframework.stereotype.Component

@Component
@ConditionalOnPropertyContains(value = "com.morak.performance-tracker.context.type", containsValue = "method", matchIfEmpty = true)
class MethodContextManager(private val descriptor: Descriptor) : ContextManager {
    private val contexts: Root = Root(ArrayList())
    private var scopes: MutableList<Scope> = ArrayList()

    override fun afterEach(accumulator: Accumulator, testMethodName: String) {
        val results = accumulator.results
        scopes.add(Scope(testMethodName, flatResults(results)))
        accumulator.clear()
    }

    private fun flatResults(results: Map<String, List<Result>>): List<Result> {
        return results.values.flatten()
    }

    override fun afterClass(accumulator: Accumulator, testClassName: String) {
        contexts.add(Context(testClassName, scopes))
        scopes = ArrayList()
    }

    override fun afterAll(accumulator: Accumulator) {
        descriptor.describe(contexts, ContextType.METHOD)
    }
}

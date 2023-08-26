package com.morak.performancetracker.context

import com.morak.performancetracker.ContextType
import com.morak.performancetracker.description.Descriptor
import com.morak.performancetracker.utils.ConditionalOnPropertyContains
import org.springframework.stereotype.Component

@Component
@ConditionalOnPropertyContains(
    value = "com.morak.performance-tracker.context.type",
    containsValue = "method",
    matchIfEmpty = true
)
class MethodContextManager(private val descriptor: Descriptor) : ContextManager {
    private val root: ResultComposite = ResultComposite(TestMetadata.ROOT);

    override fun beforeClass(testMetadata: TestMetadata) {
        this.root.findAndAdd(ResultComposite(testMetadata), testMetadata.parent)
    }

    override fun beforeEach(testMetadata: TestMetadata) {
        this.root.findAndAdd(ResultComposite(testMetadata), testMetadata.parent)
    }

    override fun afterEach(accumulator: Accumulator, testMetadata: TestMetadata) {
        accumulator.results.values
            .flatten()
            .forEach { root.findAndAdd(ResultLeaf(it.name, it.elapsed), testMetadata) }
        accumulator.clear()
    }

    override fun afterAll(accumulator: Accumulator) {
        descriptor.describe(root, ContextType.METHOD)
    }
}

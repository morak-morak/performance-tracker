package com.morak.performancetracker.description

import com.morak.performancetracker.context.Context
import com.morak.performancetracker.context.Root
import com.morak.performancetracker.context.Scope
import mu.KLogging
import mu.NamedKLogging
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component

@Component
@ConditionalOnProperty(value = ["com.morak.performance-tracker.format"], havingValue = "log", matchIfMissing = true)
class LoggingDescriptor : Descriptor {
    companion object : NamedKLogging("PERFORMANCE")

    override fun describe(root: Root) {
        root.contexts.forEach { describe(it) }
    }

    private fun describe(context: Context) {
        context.scopes.forEach { scope ->
            if (context.name == null) {
                describeScope(scope, -1)
                return@forEach
            }
            describeOnDepth(context.name, 0)
            describeScope(scope, 0)
        }
    }

    private fun describeScope(scope: Scope, depth: Int) {
        describeOnDepth(scope.name, depth + 1)
        scope.summaries.forEach { result ->
            describeOnDepth(result.result, depth + 2)
        }
    }

    private fun describeOnDepth(message: String?, depth: Int) {
        val prefix = " ".repeat(depth * 4)
        logger.info(prefix + message)
    }
}

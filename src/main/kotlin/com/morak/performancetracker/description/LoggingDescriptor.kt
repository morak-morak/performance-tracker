package com.morak.performancetracker.description

import com.morak.performancetracker.ContextType
import com.morak.performancetracker.context.Context
import com.morak.performancetracker.context.Root
import com.morak.performancetracker.context.Scope
import mu.NamedKLogging
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component


@Component
@ConditionalOnProperty(value = ["com.morak.performance-tracker.format"], havingValue = "log", matchIfMissing = true)
class LoggingDescriptor : Descriptor {
    companion object : NamedKLogging("PERFORMANCE")

    override fun describe(root: Root, contextType: ContextType) {
        logger.info("======================{}=======================", contextType.name)
        val depth = 0
        for (context in root.contexts) {
            describeContext(context, depth)
        }
    }

    private fun describeContext(context: Context, depth: Int) {
        describeOnDepth(context.name, depth)
        for (scope in context.scopes) {
            describeScope(scope, depth + 1)
        }
    }

    private fun describeScope(scope: Scope, depth: Int) {
        describeOnDepth(scope.name, depth)
        for (result in scope.summaries) {
            describeOnDepth(result.result, depth + 1)
        }
    }

    private fun describeOnDepth(message: String, depth: Int) {
        // todo : prettify prefix like tree (e.g. `brew install tree`)
        val prefix = " ".repeat(depth * 4)
        logger.info(prefix + message)
    }
}


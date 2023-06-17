package com.morak.performancetracker.description

import com.morak.performancetracker.context.Context
import com.morak.performancetracker.context.Root
import com.morak.performancetracker.context.Scope
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component

@Component
@ConditionalOnProperty(value = ["com.morak.performance-tracker.format"], havingValue = "log", matchIfMissing = true)
class LoggingDescriptor : Descriptor {
    private val log = LoggerFactory.getLogger("PERFORMANCE")
    override fun describe(root: Root) {
        for (context in root.contexts) {
            describe(context)
        }
    }

    private fun describe(context: Context) {
        for (scope in context.scopes) {
            if (context.name == null) {
                describeScope(scope, -1)
                continue
            }
            describeOnDepth(context.name, 0)
            describeScope(scope, 0)
        }
    }

    private fun describeScope(scope: Scope, depth: Int) {
        describeOnDepth(scope.name, depth + 1)
        for (result in scope.summaries) {
            describeOnDepth(result.result, depth + 2)
        }
    }

    private fun describeOnDepth(message: String?, depth: Int) {
        val prefix = " ".repeat(depth * 4)
        log.info(prefix + message)
    }
}

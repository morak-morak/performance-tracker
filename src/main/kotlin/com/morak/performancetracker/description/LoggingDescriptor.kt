package com.morak.performancetracker.description

import com.morak.performancetracker.context.Result
import com.morak.performancetracker.ContextType
import mu.NamedKLogging
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component


@Component
@ConditionalOnProperty(value = ["com.morak.performance-tracker.format"], havingValue = "log", matchIfMissing = true)
class LoggingDescriptor : Descriptor {
    companion object : NamedKLogging("PERFORMANCE")

    override fun describe(root: Result, contextType: ContextType) {
        logger.info("======================{}=======================", contextType.name)
        describeInternal(root, 0)
    }

    private fun describeInternal(entry: Result, indent: Int) {
        describeOnDepth(entry.result, indent)
        entry.subResults.forEach { describeInternal(it, indent + 1) }
    }

    private fun describeOnDepth(message: String, depth: Int) {
        val prefix = " ".repeat(depth * 4)
        logger.info(prefix + message)
    }
}

package com.morak.performancetracker.junit

import com.morak.performancetracker.PerformanceTracker
import com.morak.performancetracker.context.Accumulator
import com.morak.performancetracker.context.ContextManager
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ExtensionContext.Store.CloseableResource
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit.jupiter.SpringExtension

object PerformanceTrackerAllTestsExtension : BeforeAllCallback, CloseableResource {
    private const val STORE_KEY = "performance-tracker"

    private var started = false
    private lateinit var applicationContext: ApplicationContext
    private lateinit var contextManager: ContextManager

    private fun determineContextManager(context: ExtensionContext): Class<out ContextManager> {
        val annotation = context.requiredTestClass.getAnnotation(PerformanceTracker::class.java)
        return annotation.context.contextManagerClass.java
    }

    override fun beforeAll(context: ExtensionContext) {
        synchronized(this) {
            if (!started) {
                started = true
                applicationContext = SpringExtension.getApplicationContext(context) ?: throw IllegalArgumentException()
                contextManager = applicationContext.getBean(determineContextManager(context))
                context.root.getStore(ExtensionContext.Namespace.GLOBAL).put(STORE_KEY, this)
            }
        }
    }

    override fun close() {
        contextManager.afterAll(
            applicationContext.getBean(
                Accumulator::class.java
            )
        )
    }
}

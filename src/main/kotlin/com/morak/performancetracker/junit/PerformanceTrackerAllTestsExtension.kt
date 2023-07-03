package com.morak.performancetracker.junit

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
    private lateinit var contextManagers: List<ContextManager>

    override fun beforeAll(context: ExtensionContext) {
        synchronized(this) {
            if (!started) {
                started = true
                applicationContext = SpringExtension.getApplicationContext(context) ?: throw IllegalArgumentException()
                contextManagers = applicationContext.getBeansOfType(ContextManager::class.java).values.toList()
                context.root.getStore(ExtensionContext.Namespace.GLOBAL).put(STORE_KEY, this)
            }
        }
    }

    override fun close() {
        contextManagers.forEach { it.afterAll(applicationContext.getBean(Accumulator::class.java)) }
    }
}

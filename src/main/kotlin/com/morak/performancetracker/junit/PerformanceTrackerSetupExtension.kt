package com.morak.performancetracker.junit

import com.morak.performancetracker.PerformanceTracker
import com.morak.performancetracker.context.Accumulator
import com.morak.performancetracker.context.ContextManager
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit.jupiter.SpringExtension

class PerformanceTrackerSetupExtension : AfterEachCallback, AfterAllCallback {
    override fun afterEach(context: ExtensionContext) {
        if (context.executionException.isPresent) {
            return
        }

        val applicationContext = getApplicationContext(context)
        val manager = applicationContext.getBean(determineContextManager(context))
        manager.afterEach(applicationContext.getBean(Accumulator::class.java), context.requiredTestMethod.name)
    }

    override fun afterAll(context: ExtensionContext) {
        val applicationContext = getApplicationContext(context)
        val manager = applicationContext.getBean(determineContextManager(context))
        manager.afterClass(applicationContext.getBean(Accumulator::class.java), context.requiredTestClass.name)
    }

    private fun determineContextManager(context: ExtensionContext): Class<out ContextManager> {
        val annotation = context.requiredTestClass.getAnnotation(PerformanceTracker::class.java)
        return annotation.context.contextManagerClass.java
    }

    private fun getApplicationContext(context: ExtensionContext): ApplicationContext {
        return SpringExtension.getApplicationContext(context)
    }
}

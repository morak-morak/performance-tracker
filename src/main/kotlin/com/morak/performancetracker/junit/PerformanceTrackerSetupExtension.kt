package com.morak.performancetracker.junit

import com.morak.performancetracker.PerformanceTracker
import com.morak.performancetracker.context.Accumulator
import com.morak.performancetracker.context.ContextManager
import com.morak.performancetracker.context.TestMetadata
import org.junit.jupiter.api.extension.*
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit.jupiter.SpringExtension

class PerformanceTrackerSetupExtension : BeforeAllCallback, BeforeEachCallback, AfterEachCallback, AfterAllCallback {

    override fun beforeAll(context: ExtensionContext) {
        val applicationContext = getApplicationContext(context)
        val manager = applicationContext.getBean(determineContextManager(context))
        val testMetadata = TestMetadata.of(context.requiredTestClass.name)
        manager.beforeClass(testMetadata)
    }

    override fun beforeEach(context: ExtensionContext) {
        val applicationContext = getApplicationContext(context)
        val manager = applicationContext.getBean(determineContextManager(context))
        val testMetadata = TestMetadata.of(context.requiredTestClass.name, context.requiredTestMethod.name)
        manager.beforeEach(testMetadata)
    }

    override fun afterEach(context: ExtensionContext) {
        if (context.executionException.isPresent) {
            return
        }

        val applicationContext = getApplicationContext(context)
        val manager = applicationContext.getBean(determineContextManager(context))
        val testMetadata = TestMetadata.of(context.requiredTestClass.name, context.requiredTestMethod.name)
        manager.afterEach(applicationContext.getBean(Accumulator::class.java), testMetadata)
    }

    override fun afterAll(context: ExtensionContext) {
        val applicationContext = getApplicationContext(context)
        val manager = applicationContext.getBean(determineContextManager(context))
        val testMetadata = TestMetadata.of(context.requiredTestClass.name)
        manager.afterClass(applicationContext.getBean(Accumulator::class.java), testMetadata)
    }

    private fun determineContextManager(context: ExtensionContext): Class<out ContextManager> {
        val annotation = context.requiredTestClass.getAnnotation(PerformanceTracker::class.java)
        return annotation.context.contextManagerClass.java
    }

    private fun getApplicationContext(context: ExtensionContext): ApplicationContext {
        return SpringExtension.getApplicationContext(context)
    }
}

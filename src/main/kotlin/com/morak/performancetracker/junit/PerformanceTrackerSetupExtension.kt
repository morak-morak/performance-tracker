package com.morak.performancetracker.junit

import com.morak.performancetracker.context.Accumulator
import com.morak.performancetracker.context.ContextManager
import com.morak.performancetracker.context.TestMetadata
import org.junit.jupiter.api.extension.*
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.function.Consumer


class PerformanceTrackerSetupExtension : BeforeAllCallback, BeforeEachCallback, AfterEachCallback, AfterAllCallback {

    override fun beforeAll(context: ExtensionContext) {
        val applicationContext = getApplicationContext(context)
        val testMetadata = TestMetadata.of(context.requiredTestClass.name)
        applicationContext.getBeansOfType(ContextManager::class.java)
            .values
            .forEach { it: ContextManager ->
                it.beforeClass(
                    testMetadata
                )
            }
    }

    override fun beforeEach(context: ExtensionContext) {
        val applicationContext = getApplicationContext(context)
        val testMetadata = TestMetadata.of(context.requiredTestClass.name, context.requiredTestMethod.name)
        applicationContext.getBeansOfType(ContextManager::class.java)
            .values
            .forEach { it: ContextManager ->
                it.beforeEach(
                    testMetadata
                )
            }
    }

    override fun afterEach(context: ExtensionContext) {
        val applicationContext = getApplicationContext(context)
        val accumulator = applicationContext.getBean(
            Accumulator::class.java
        )
        val testMetadata = TestMetadata.of(context.requiredTestClass.name, context.requiredTestMethod.name)

        applicationContext.getBeansOfType(ContextManager::class.java)
            .values
            .forEach { it: ContextManager ->
                it.afterEach(
                    accumulator,
                    testMetadata
                )
            }
        accumulator.clear()
    }

    override fun afterAll(context: ExtensionContext) {
        val applicationContext = getApplicationContext(context)
        val testMetadata = TestMetadata.of(context.requiredTestClass.name)
        applicationContext.getBeansOfType(ContextManager::class.java)
            .values
            .forEach { it: ContextManager ->
                it.afterClass(
                    applicationContext.getBean(Accumulator::class.java),
                    testMetadata
                )
            }
    }

    private fun getApplicationContext(context: ExtensionContext): ApplicationContext {
        return SpringExtension.getApplicationContext(context)
    }
}

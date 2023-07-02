package com.morak.performancetracker.junit

import com.morak.performancetracker.context.Accumulator
import com.morak.performancetracker.context.ContextManager
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.function.Consumer


class PerformanceTrackerSetupExtension : AfterEachCallback, AfterAllCallback {
    override fun afterEach(context: ExtensionContext) {
        val applicationContext = getApplicationContext(context)
        val accumulator = applicationContext.getBean(
            Accumulator::class.java
        )
        applicationContext.getBeansOfType(ContextManager::class.java)
            .values
            .forEach(Consumer { manager: ContextManager ->
                manager.afterEach(
                    accumulator,
                    context.requiredTestMethod.name
                )
            })
        accumulator.clear()
    }

    override fun afterAll(context: ExtensionContext) {
        val applicationContext = getApplicationContext(context)
        applicationContext.getBeansOfType(ContextManager::class.java)
            .values
            .forEach { manager: ContextManager ->
                manager.afterClass(
                    applicationContext.getBean(Accumulator::class.java),
                    context.requiredTestClass.name
                )
            }
    }

    private fun getApplicationContext(context: ExtensionContext): ApplicationContext {
        return SpringExtension.getApplicationContext(context)
    }
}

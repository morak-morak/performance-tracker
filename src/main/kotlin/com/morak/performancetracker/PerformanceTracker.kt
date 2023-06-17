package com.morak.performancetracker

import com.morak.performancetracker.configuration.PerformanceConfiguration
import com.morak.performancetracker.junit.PerformanceTrackerAllTestsExtension
import com.morak.performancetracker.junit.PerformanceTrackerSetupExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.context.annotation.Import

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@ExtendWith(PerformanceTrackerSetupExtension::class, PerformanceTrackerAllTestsExtension::class)
@Import(PerformanceConfiguration::class)
annotation class PerformanceTracker(val context: ContextType = ContextType.ENDPOINT)

package com.morak.performancetracker.utils

import org.springframework.context.annotation.Condition
import org.springframework.context.annotation.ConditionContext
import org.springframework.context.annotation.Conditional
import org.springframework.core.type.AnnotatedTypeMetadata


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Conditional(ConditionalOnPropertyContains.PropertyContainsCondition::class)
annotation class ConditionalOnPropertyContains(
    val value: String = "",
    val containsValue: String = "",
    val matchIfEmpty: Boolean = false,
) {
    class PropertyContainsCondition : Condition {
        companion object {
            private val DELIMITER = Regex(", ?")
        }

        override fun matches(context: ConditionContext, metadata: AnnotatedTypeMetadata): Boolean {
            val attrs = metadata.getAnnotationAttributes(ConditionalOnPropertyContains::class.java.name) ?: return false
            val propertyName = attrs["value"] as String? ?: return false
            val containsValue = attrs["containsValue"] as String?
            val matchIfEmpty = attrs["matchIfEmpty"] as Boolean
            val value = context.environment.getProperty(propertyName) ?: ""

            if (matchIfEmpty && value.isEmpty()) {
                return true
            }
            return value.split(DELIMITER).contains(containsValue).also { println("it = ${it}") }
        }
    }
}



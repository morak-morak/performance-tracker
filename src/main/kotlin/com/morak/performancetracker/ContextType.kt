package com.morak.performancetracker

import com.morak.performancetracker.context.ContextManager
import com.morak.performancetracker.context.EndpointContextManager
import com.morak.performancetracker.context.MethodContextManager
import kotlin.reflect.KClass

enum class ContextType(val contextManagerClass: KClass<out ContextManager>) {
    METHOD(MethodContextManager::class),
    ENDPOINT(EndpointContextManager::class)
}

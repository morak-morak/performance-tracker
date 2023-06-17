package com.morak.performancetracker

import com.morak.performancetracker.context.ContextManager
import com.morak.performancetracker.context.EndpointContextManager
import com.morak.performancetracker.context.MethodContextManager

enum class ContextType(val contextManagerClass: Class<out ContextManager>) {
    METHOD(MethodContextManager::class.java),
    ENDPOINT(EndpointContextManager::class.java)
}

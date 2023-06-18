package com.morak.performancetracker.context

class Root(contexts: List<Context>) {
    private var _contexts = contexts.toMutableList()
    val contexts: List<Context>
        get() = _contexts

    fun add(context: Context) {
        _contexts.add(context)
    }
}

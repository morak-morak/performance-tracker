package com.morak.performancetracker.context

class Root(val contexts: MutableList<Context>) {
    fun add(context: Context) {
        contexts.add(context)
    }
}

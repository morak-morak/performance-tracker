package com.morak.performancetracker.aop.persistence

import com.morak.performancetracker.Monitor
import org.springframework.stereotype.Component

@Component
class QueryMonitor(
    query: String? = null
) : Monitor {
    var _query = query
    var query: String
        get() = _query!!
        set(v: String) {
            _query = v
        }

    var startTime: Long = 0
    var queryTime = 0.0
    override fun clear() {
        _query = null
        startTime = 0
        queryTime = 0.0
    }

    override val signature: String
        get() = "Query"

    fun start() {
        startTime = System.nanoTime()
    }

    fun end() {
        check(startTime != 0L) { "Monitor not initialized yet" }
        queryTime += (System.nanoTime() - startTime).toDouble()
    }
}

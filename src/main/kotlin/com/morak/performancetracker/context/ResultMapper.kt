package com.morak.performancetracker.context

import com.morak.performancetracker.Monitor
import com.morak.performancetracker.aop.persistence.QueryMonitor
import com.morak.performancetracker.aop.rest.RestMonitor
import com.morak.performancetracker.aop.web.WebMonitor
import org.springframework.stereotype.Component

@Component
class ResultMapper {
    fun mapMonitor(monitor: Monitor): Result {
        val result = mapToResult(monitor)
        monitor.clear()
        return result
    }

    private fun mapToResult(monitor: Monitor): Result {
        return when (monitor) {
            is QueryMonitor -> Result(monitor.query!!, monitor.queryTime)
            is WebMonitor -> Result(monitor.method + " " + monitor.uri, monitor.elapsed.toDouble())
            is RestMonitor -> Result(monitor.uri!!, monitor.elapsedTime.toDouble())
            else -> throw IllegalArgumentException("Couldn't find proper monitor")
        }
    }
}

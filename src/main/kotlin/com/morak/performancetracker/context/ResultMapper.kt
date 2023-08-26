package com.morak.performancetracker.context

import com.morak.performancetracker.Monitor
import com.morak.performancetracker.aop.persistence.QueryMonitor
import com.morak.performancetracker.aop.rest.RestMonitor
import com.morak.performancetracker.aop.web.WebMonitor
import org.springframework.stereotype.Component

@Component
class ResultMapper {
    fun mapMonitor(monitor: Monitor): MonitorResult {
        val result = mapToResult(monitor)
        monitor.clear()
        return result
    }

    private fun mapToResult(monitor: Monitor): MonitorResult {
        return when (monitor) {
            // todo: remove "!!"
            is QueryMonitor -> MonitorResult(monitor.query!!, monitor.queryTime)
            is WebMonitor -> MonitorResult(monitor.method + " " + monitor.uri, monitor.elapsed.toDouble())
            is RestMonitor -> MonitorResult(monitor.uri!!, monitor.elapsedTime.toDouble())
            else -> throw IllegalArgumentException("Couldn't find proper monitor")
        }
    }
}

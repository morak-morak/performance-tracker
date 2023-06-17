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
        if (monitor is QueryMonitor) {
            val qm = monitor
            return Result(qm.query, qm.queryTime)
        }
        if (monitor is WebMonitor) {
            val wm = monitor
            return Result(wm.method + " " + wm.uri, wm.elapsed.toDouble())
        }
        if (monitor is RestMonitor) {
            val rm = monitor
            return Result(rm.uri, rm.elapsedTime.toDouble())
        }
        throw IllegalArgumentException("Couldn't find proper monitor")
    }
}

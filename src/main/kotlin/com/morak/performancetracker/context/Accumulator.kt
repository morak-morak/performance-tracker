package com.morak.performancetracker.context

import com.morak.performancetracker.Monitor
import org.springframework.stereotype.Component

@Component
class Accumulator(private val resultMapper: ResultMapper) {
    private var _results: MutableMap<String, MutableList<MonitorResult>> = mutableMapOf()
    val results: Map<String, List<MonitorResult>>
        get() = _results

    fun add(monitor: Monitor) {
        val list = _results.getOrPut(monitor.signature) { mutableListOf() }
        list.add(resultMapper.mapMonitor(monitor))
    }

    fun clear() {
        _results.clear()
    }
}

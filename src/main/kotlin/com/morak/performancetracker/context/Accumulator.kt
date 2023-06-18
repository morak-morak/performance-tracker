package com.morak.performancetracker.context

import com.morak.performancetracker.Monitor
import org.springframework.stereotype.Component

@Component
class Accumulator(private val resultMapper: ResultMapper) {
    private var _results: MutableMap<String, MutableList<Result>> = mutableMapOf()
    val results: Map<String, List<Result>>
        get() = _results

    fun add(monitor: Monitor) {
        val signature = monitor.signature
        _results.computeIfAbsent(signature) { _: String? -> ArrayList() }
        _results[signature]!!.add(resultMapper.mapMonitor(monitor))
    }

    fun clear() {
        _results.clear()
    }
}

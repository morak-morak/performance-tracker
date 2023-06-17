package com.morak.performancetracker.aop.rest

import com.morak.performancetracker.Monitor
import org.springframework.stereotype.Component

@Component
class RestMonitor : Monitor {
    private var _uri: String? = null
    val uri: String
        get() = uri!!
    var startTime: Long = 0
    var elapsedTime: Long = 0

    fun start(uri: String?) {
        this._uri = uri
        startTime = System.nanoTime()
    }

    fun end() {
        elapsedTime = System.nanoTime() - startTime
    }

    override fun clear() {
        _uri = null
        startTime = 0
        elapsedTime = 0
    }

    override val signature: String
        get() = "REST"
}

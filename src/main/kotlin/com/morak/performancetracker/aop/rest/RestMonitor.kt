package com.morak.performancetracker.aop.rest

import com.morak.performancetracker.Monitor
import org.springframework.stereotype.Component

@Component
class RestMonitor : Monitor {
    var uri: String? = null
    var startTime: Long = 0
    var elapsedTime: Long = 0

    fun start(uri: String?) {
        this.uri = uri
        startTime = System.nanoTime()
    }

    fun end() {
        elapsedTime = System.nanoTime() - startTime
    }

    override fun clear() {
        uri = null
        startTime = 0
        elapsedTime = 0
    }

    override val signature: String
        get() = "REST"
}

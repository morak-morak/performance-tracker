package com.morak.performancetracker.aop.web

import com.morak.performancetracker.Monitor
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

@Component
class WebMonitor : Monitor {
    var uri: String? = null
    var method: String? = null
    private var requestTime: Long = 0
    var elapsed: Long = 0

    fun start(request: HttpServletRequest) {
        uri = request.requestURI
        method = request.method
        requestTime = System.nanoTime()
    }

    fun end() {
        elapsed = System.nanoTime() - requestTime
    }

    override fun clear() {
        uri = null
        method = null
        requestTime = 0
        elapsed = 0
    }

    override val signature: String
        get() = "Web"

    fun getRequestTime(): Double {
        return requestTime.toDouble()
    }
}

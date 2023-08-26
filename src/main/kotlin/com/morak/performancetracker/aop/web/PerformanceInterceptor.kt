package com.morak.performancetracker.aop.web

import com.morak.performancetracker.context.Accumulator
import org.springframework.http.HttpMethod
import org.springframework.lang.Nullable
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class PerformanceInterceptor(private val accumulator: Accumulator, private val webMonitor: WebMonitor) :
    HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (!isPreflight(request)) {
            webMonitor.start(request)
        }
        return true
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        @Nullable modelAndView: ModelAndView?
    ) {
        if (isPreflight(request) || webMonitor.uri == null) {
            return
        }
        webMonitor.end()
        accumulator.add(webMonitor)
    }

    private fun isPreflight(request: HttpServletRequest): Boolean {
        return request.method == HttpMethod.OPTIONS.name
    }
}

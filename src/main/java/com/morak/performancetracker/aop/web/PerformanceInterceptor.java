package com.morak.performancetracker.aop.web;

import com.morak.performancetracker.context.Accumulator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class PerformanceInterceptor implements HandlerInterceptor {

    private final Accumulator accumulator;
    private final WebMonitor webMonitor;

    public PerformanceInterceptor(Accumulator accumulator, WebMonitor webMonitor) {
        this.accumulator = accumulator;
        this.webMonitor = webMonitor;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!isPreflight(request)) {
            webMonitor.start(request);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {
        if (isPreflight(request) || webMonitor.getUri() == null) {
            return;
        }
        webMonitor.end();
        accumulator.add(webMonitor);
    }

    private boolean isPreflight(HttpServletRequest request) {
        return request.getMethod().equals("OPTIONS");
    }
}

package com.morak.performancetracker.aop.web;

import com.morak.performancetracker.utils.TimeUnits;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class WebMonitor {
    private String uri;
    private String method;
    private long requestTime;
    private long elapsed;

    public void start(HttpServletRequest request) {
        uri = request.getRequestURI();
        method = request.getMethod();
        requestTime = System.nanoTime();
    }

    public void end() {
        this.elapsed = System.nanoTime() - requestTime;
    }

    public void clear() {
        uri = null;
        method = null;
        requestTime = 0;
        elapsed = 0;
    }

    public String getUri() {
        return uri;
    }

    public String getMethod() {
        return method;
    }

    public double getRequestTime() {
        return requestTime;
    }

    @Override
    public String toString() {
        return "WebMonitor{" +
                "uri='" + uri + '\'' +
                ", method='" + method + '\'' +
                ", elapsed=" + TimeUnits.convertNanoToMilli(elapsed) +
                '}';
    }
}

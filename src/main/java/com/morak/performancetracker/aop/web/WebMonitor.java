package com.morak.performancetracker.aop.web;

import com.morak.performancetracker.Monitor;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class WebMonitor implements Monitor {

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

    @Override
    public void clear() {
        uri = null;
        method = null;
        requestTime = 0;
        elapsed = 0;
    }

    @Override
    public String getSignature() {
        return "Web";
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

    public long getElapsed() {
        return elapsed;
    }
}

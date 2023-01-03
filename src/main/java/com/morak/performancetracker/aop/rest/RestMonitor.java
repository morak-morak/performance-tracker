package com.morak.performancetracker.aop.rest;

import com.morak.performancetracker.Monitor;
import org.springframework.stereotype.Component;

@Component
public class RestMonitor implements Monitor {

    private String uri = null;
    private long startTime = 0;
    private long elapsedTime = 0;

    public void start(String uri) {
        this.uri = uri;
        this.startTime = System.nanoTime();
    }

    public void end() {
        this.elapsedTime = this.startTime - elapsedTime;
    }

    @Override
    public void clear() {
        this.uri = null;
        this.startTime = 0;
        this.elapsedTime = 0;
    }

    @Override
    public String getSignature() {
        return "REST";
    }

    public String getUri() {
        return uri;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }
}

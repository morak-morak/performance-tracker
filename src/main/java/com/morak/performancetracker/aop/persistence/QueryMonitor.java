package com.morak.performancetracker.aop.persistence;

import com.morak.performancetracker.Monitor;
import org.springframework.stereotype.Component;

@Component
public class QueryMonitor implements Monitor {

    private String query;
    private long startTime;
    private double queryTime;

    public void start() {
        startTime = System.nanoTime();
    }

    public void end() {
        if (startTime == 0) {
            throw new IllegalStateException("Monitor not initialized yet");
        }
        this.queryTime += System.nanoTime() - startTime;
    }

    @Override
    public void clear() {
        this.query = null;
        this.startTime = 0;
        this.queryTime = 0;
    }

    @Override
    public String getSignature() {
        return "Query";
    }

    public long getStartTime() {
        return startTime;
    }

    public double getQueryTime() {
        return queryTime;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}

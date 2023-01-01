package com.morak.performancetracker.aop.persistence;

import com.morak.performancetracker.Monitor;
import org.springframework.stereotype.Component;

@Component
public class QueryMonitor implements Monitor {

    private static final double WARNING_QUERY_COUNT = 5;
    private static final double WARNING_QUERY_TIME = 100 * 1_000_000.0;

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
        this.startTime = 0;
        this.queryTime = 0;
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

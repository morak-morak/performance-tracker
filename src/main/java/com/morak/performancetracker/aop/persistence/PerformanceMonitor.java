package com.morak.performancetracker.aop.persistence;

import org.springframework.stereotype.Component;

@Component
public class PerformanceMonitor {

    private static final double WARNING_QUERY_COUNT = 5;
    private static final double WARNING_QUERY_TIME = 100 * 1_000_000.0;

    private long startTime;
    private int queryCount;
    private double queryTime;

    public void start() {
        startTime = System.nanoTime();
    }

    public void end() {
        this.queryTime += System.nanoTime() - startTime;
        queryCount++;
    }

    private double convertNanoToMilli(double nano) {
        return nano / 1_000_000.0;
    }

    @Override
    public String toString() {
        return String.format(
                "쿼리 개수: %d, 쿼리 시간: %f ms",
                queryCount,
                convertNanoToMilli(queryTime)
        );
    }
}

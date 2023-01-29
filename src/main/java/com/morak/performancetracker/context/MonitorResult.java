package com.morak.performancetracker.context;

public class MonitorResult {

    private final String name;
    private final double elapsed;

    public MonitorResult(String name, double elapsed) {
        this.name = name;
        this.elapsed = elapsed;
    }

    public String getName() {
        return name;
    }

    public double getElapsed() {
        return elapsed;
    }
}

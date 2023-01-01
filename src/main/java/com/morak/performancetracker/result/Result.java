package com.morak.performancetracker.result;

public class Result {

    private final String name;
    private final double elapsed;

    public Result(String name, double elapsed) {
        this.name = name;
        this.elapsed = elapsed;
    }

    public String getResult() {
        return toString();
    }

    @Override
    public String toString() {
        return "Result{" +
                "name='" + name + '\'' +
                ", elapsed=" + elapsed +
                '}';
    }
}

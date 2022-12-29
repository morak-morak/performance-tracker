package com.morak.performancetracker.result;

import com.morak.performancetracker.Monitor;

public class Result {

    private final String result;

    public Result(String result) {
        this.result = result;
    }

    public static Result of(Monitor monitor) {
        return new Result(monitor.getResult());
    }

    public String getResult() {
        return result;
    }
}

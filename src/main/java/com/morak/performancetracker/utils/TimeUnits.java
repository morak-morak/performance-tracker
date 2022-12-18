package com.morak.performancetracker.utils;

public class TimeUnits {

    private TimeUnits() {
        throw new IllegalStateException();
    }

    public static double convertNanoToMilli(double nano) {
        return nano / 1_000_000.0;
    }
}

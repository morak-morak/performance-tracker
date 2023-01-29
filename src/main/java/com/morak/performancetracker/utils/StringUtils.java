package com.morak.performancetracker.utils;

public class StringUtils {

    private StringUtils() {
    }

    public static String substringFrom(String str, char delimiter) {
        int lastIndex = str.lastIndexOf(delimiter);
        if (lastIndex == -1) {
            return str;
        }
        return str.substring(lastIndex + 1);
    }

    public static String substringUntil(String str, char delimiter) {
        int lastIndex = str.lastIndexOf(delimiter);
        if (lastIndex == -1) {
            return str;
        }
        return str.substring(0, lastIndex);
    }
}

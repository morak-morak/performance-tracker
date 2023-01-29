package com.morak.performancetracker.utils;

import java.util.List;

public class ListUtils {
    private ListUtils() {
    }

    public static boolean isNullOrEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }
}

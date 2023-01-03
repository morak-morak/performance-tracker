package com.morak.performancetracker.context;

import java.util.List;

public class Scope {

    private final String name;
    private final List<Result> summaries;

    public Scope(String name, List<Result> summaries) {
        this.name = name;
        this.summaries = summaries;
    }

    public String getName() {
        return name;
    }

    public List<Result> getSummaries() {
        return summaries;
    }
}

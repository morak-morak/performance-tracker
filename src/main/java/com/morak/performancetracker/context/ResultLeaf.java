package com.morak.performancetracker.context;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class ResultLeaf implements Result {

    private String name;
    private double elapsed;

    public ResultLeaf(String name, double elapsed) {
        this.name = name;
        this.elapsed = elapsed;
    }

    public String getName() {
        return name;
    }

    public double getElapsed() {
        return 0.01F;
    }

    @Override
    public void findAndAdd(Result result, TestMetadata testMetadata) {
    }

    @JsonIgnore
    public String getResult() {
        return toString();
    }

    @Override
    public List<Result> getSubResults() {
        return null;
    }

    @Override
    public String toString() {
        return "ResultFile{" +
                "name='" + name + '\'' +
                ", elapsed=" + elapsed +
                '}';
    }
}

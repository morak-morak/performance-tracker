package com.morak.performancetracker.context;

import java.util.List;

public class ResultComposite implements Result {

    private final TestMetadata testMetadata;
    private final List<Result> subResults;

    public ResultComposite(TestMetadata testMetadata, List<Result> subResults) {
        this.testMetadata = testMetadata;
        this.subResults = subResults;
    }

    @Override
    public void findAndAdd(Result result, TestMetadata testMetadata) {
        if (this.testMetadata.equals(testMetadata)) {
            this.subResults.add(result);
            return;
        }
        subResults.forEach(subResult -> subResult.findAndAdd(result, testMetadata));
    }

    @Override
    public String getResult() {
        return this.testMetadata.self();
    }

    @Override
    public List<Result> getSubResults() {
        return subResults;
    }
}

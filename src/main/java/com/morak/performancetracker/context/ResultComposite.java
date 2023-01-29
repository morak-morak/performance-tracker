package com.morak.performancetracker.context;

import java.util.List;

public class ResultComposite implements Result {

    private TestMetadata testMetadata;
    private List<Result> subResults;

    public ResultComposite(TestMetadata testMetadata, List<Result> subResults) {
        this.testMetadata = testMetadata;
        this.subResults = subResults;
    }

    @Override
    public void findAndAdd(Result result, TestMetadata testMetadata) {
        if (this.testMetadata.getClassName().equals(testMetadata.getClassName())) {
            this.subResults.add(result);
        }
        subResults.forEach(subResult -> subResult.findAndAdd(result, testMetadata));
    }

    @Override
    public String getResult() {
        return this.testMetadata.toString();
    }

    @Override
    public List<Result> getSubResults() {
        return subResults;
    }

    public void add(ResultComposite resultComposite) {
        this.subResults.add(resultComposite);
    }
}

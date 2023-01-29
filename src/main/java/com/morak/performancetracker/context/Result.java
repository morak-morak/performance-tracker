package com.morak.performancetracker.context;

import java.util.List;

public interface Result {

    void findAndAdd(Result result, TestMetadata testMetadata);

    String getResult();

    List<Result> getSubResults();
}

package com.morak.performancetracker.description;

import com.morak.performancetracker.result.Result;

public interface Descriptor {

    void describe(String methodName);

    void describe(Result result);
}

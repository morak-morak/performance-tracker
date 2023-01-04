package com.morak.performancetracker.description;

import com.morak.performancetracker.context.Context;
import com.morak.performancetracker.context.Result;

public interface Descriptor {

    void describe(String methodName);

    void describe(Result result);

    void describe(Context context);
}

package com.morak.performancetracker.context;

import com.morak.performancetracker.Monitor;
import java.util.Collection;

public interface ContextManager {

    void afterEach(Collection<Monitor> monitors);

    void afterAll(Collection<Monitor> monitors);
}

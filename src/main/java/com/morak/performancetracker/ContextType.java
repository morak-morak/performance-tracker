package com.morak.performancetracker;

import com.morak.performancetracker.context.ContextManager;
import com.morak.performancetracker.context.EndpointContextManager;
import com.morak.performancetracker.context.MethodContextManager;

public enum ContextType {
    METHOD(MethodContextManager.class),
    ENDPOINT(EndpointContextManager.class);

    private final Class<? extends ContextManager> contextManagerClass;

    ContextType(Class<? extends ContextManager> contextManagerClass) {
        this.contextManagerClass = contextManagerClass;
    }

    public Class<? extends ContextManager> getContextManagerClass() {
        return contextManagerClass;
    }
}

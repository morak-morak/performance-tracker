package com.morak.performancetracker.context;

import java.util.List;

public class Root {

    private final List<Context> contexts;

    public Root(List<Context> contexts) {
        this.contexts = contexts;
    }

    public void add(Context context) {
        contexts.add(context);
    }

    public List<Context> getContexts() {
        return contexts;
    }
}

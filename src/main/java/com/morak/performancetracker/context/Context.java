package com.morak.performancetracker.context;

import java.util.List;

public class Context {

    private final String name;
    private final List<Scope> scopes;

    public Context(String name, List<Scope> scopes) {
        this.name = name;
        this.scopes = scopes;
    }

    public List<Scope> getScopes() {
        return scopes;
    }

    public String getName() {
        return this.name;
    }
}

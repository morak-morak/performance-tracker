package com.morak.performancetracker.context;

import java.util.List;

public class Context {

    private List<Scope> scopes;

    public Context(List<Scope> scopes) {
        this.scopes = scopes;
    }

    public List<Scope> getScopes() {
        return scopes;
    }
}

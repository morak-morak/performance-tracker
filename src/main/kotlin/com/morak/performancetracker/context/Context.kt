package com.morak.performancetracker.context

class Context {
    val name: String?
    val scopes: List<Scope>

    constructor(scopes: List<Scope>) {
        name = null
        this.scopes = scopes
    }

    constructor(name: String?, scopes: List<Scope>) {
        this.name = name
        this.scopes = scopes
    }
}

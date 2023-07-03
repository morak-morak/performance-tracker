package com.morak.performancetracker.description

import com.morak.performancetracker.context.Result

interface Descriptor {
    fun describe(root: Result)
}

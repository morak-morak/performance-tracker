package com.morak.performancetracker.description

import com.morak.performancetracker.context.Root

interface Descriptor {
    fun describe(root: Root)
}

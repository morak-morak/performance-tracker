package com.morak.performancetracker

interface Monitor {
    fun clear()
    val signature: String
    // todo : val identifier
}

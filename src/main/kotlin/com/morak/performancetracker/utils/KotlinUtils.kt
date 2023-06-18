package com.morak.performancetracker.utils

fun <T> List<T>.average(selector: (T) -> Double): Double {
    if (this.isEmpty()) return 0.toDouble()
    return this.sumOf(selector) / this.size
}
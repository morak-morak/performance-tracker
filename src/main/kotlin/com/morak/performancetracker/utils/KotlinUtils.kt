package com.morak.performancetracker.utils

fun <T> List<T>.average(selector: (T) -> Double): Double {
    if (this.isEmpty()) return 0.toDouble()
    return this.sumOf(selector) / this.size
}

fun String.substringUntil(delimiter: Char): String {
    val lastIndex = this.lastIndexOf(delimiter)
    return if (lastIndex == -1) this
    else this.substring(lastIndex + 1)
}

fun String.substringFrom(delimiter: Char): String {
    val lastIndex = this.lastIndexOf(delimiter)
    return if (lastIndex == -1) this
    else this.substring(0, lastIndex)
}

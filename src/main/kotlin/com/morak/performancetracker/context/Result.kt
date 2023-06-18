package com.morak.performancetracker.context

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.concurrent.TimeUnit

class Result(val name: String, val elapsed: Double) {

    @get:JsonIgnore
    val result: String
        get() = toString()

    override fun toString(): String =
        "Result{name='$name', elapsed=${TimeUnit.MILLISECONDS.convert(elapsed.toLong(), TimeUnit.NANOSECONDS)}ms}"
}

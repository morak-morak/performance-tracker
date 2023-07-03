package com.morak.performancetracker.context

import com.morak.performancetracker.utils.substringFrom
import com.morak.performancetracker.utils.substringUntil

class TestMetadata(private val name: String) {
    fun parent(): TestMetadata {
        return TestMetadata(name.substringUntil('@'))
    }

    fun self(): String {
        return name.substringFrom('@')
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TestMetadata

        return name == other.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun toString(): String {
        return "TestMetadata{" +
                "name='" + self() + '\'' +
                '}'
    }


    companion object {
        private const val ROOT_NAME = "ROOT"
        private const val NAME_DELIMITER = "@"

        val ROOT = TestMetadata(ROOT_NAME)

        fun of(vararg names: String): TestMetadata {
            val name = ROOT_NAME.plus(NAME_DELIMITER).plus(names.reduce { a, b -> a.plus(NAME_DELIMITER).plus(b) })
            return TestMetadata(name)
        }
    }
}

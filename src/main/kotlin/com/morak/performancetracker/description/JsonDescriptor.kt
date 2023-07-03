package com.morak.performancetracker.description

import com.fasterxml.jackson.databind.ObjectMapper
import com.morak.performancetracker.configuration.DescriptorProperties
import com.morak.performancetracker.context.Result
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
@ConditionalOnProperty(value = ["com.morak.performance-tracker.format"], havingValue = "json")
class JsonDescriptor(
    descriptorProperties: DescriptorProperties,
    private val objectMapper: ObjectMapper,
) : Descriptor {
    private val path: String = descriptorProperties.path
    private val file: String = descriptorProperties.file
    private val today: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_POSTFIX))

    override fun describe(root: Result) {
        val jsonFile = File(path + file + today + JSON_FORMAT)
        runCatching {
            FileWriter(jsonFile, true)
                .use { fileWriter -> fileWriter.write(objectMapper.writeValueAsString(root)) }
        }.onFailure { throw RuntimeException("I/O error writing context") }
    }

    companion object {
        private const val JSON_FORMAT = ".json"
        private const val DATE_TIME_POSTFIX = "-yyyy-MM-dd-HH:mm:ss"
    }
}

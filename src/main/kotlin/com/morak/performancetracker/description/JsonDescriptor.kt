package com.morak.performancetracker.description

import com.fasterxml.jackson.databind.ObjectMapper
import com.morak.performancetracker.configuration.DescriptorProperties
import com.morak.performancetracker.context.Root
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

@Component
@ConditionalOnProperty(value = ["com.morak.performance-tracker.format"], havingValue = "json")
class JsonDescriptor(
    descriptorProperties: DescriptorProperties,
    private val objectMapper: ObjectMapper
) : Descriptor {
    private val path: String?
    private val file: String?

    init {
        path = descriptorProperties.path
        file = descriptorProperties.file
    }

    override fun describe(root: Root) {
        val jsonFile = File(path + createFileName() + JSON_FORMAT)
        try {
            FileWriter(jsonFile, true).use { fileWriter ->
                objectMapper.writer().writeValuesAsArray(fileWriter).use { seqWriter -> seqWriter.write(root) }
            }
        } catch (e: IOException) {
            throw RuntimeException("I/O error writing context")
        }
    }

    private fun createFileName(): String {
        return file + nowDate
    }

    private val nowDate: String
        private get() {
            val dateFormat = SimpleDateFormat("-yyyy-MM-dd-HH:mm:ss")
            val date = Date()
            return dateFormat.format(date)
        }

    companion object {
        private const val JSON_FORMAT = ".json"
    }
}

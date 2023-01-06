package com.morak.performancetracker.description;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.morak.performancetracker.context.Context;
import com.morak.performancetracker.context.Scope;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "com.morak.performance-tracker.format", havingValue = "json")
public class JsonDescriptor implements Descriptor {

    private static final String JSON_FORMAT = ".json";

    @Value("${com.morak.performance-tracker.logs.path:./logs/}")
    private String filePath;

    @Value("${com.morak.performance-tracker.logs.file:performance}")
    private String fileName;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void describe(Context context) {
        File file = new File(filePath + fileName + JSON_FORMAT);
        try (FileWriter fileWriter = new FileWriter(file, true);
             SequenceWriter seqWriter = objectMapper.writer().writeValuesAsArray(fileWriter)) {
            writeToFile(context, seqWriter);
        } catch (IOException e) {
            throw new RuntimeException("I/O error writing context");
        }
    }

    private void writeToFile(Context context, SequenceWriter seqWriter) throws IOException {
        for (Scope scope : context.getScopes()) {
            seqWriter.write(scope);
        }
    }
}
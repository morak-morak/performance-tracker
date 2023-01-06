package com.morak.performancetracker.description;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.morak.performancetracker.context.Context;
import com.morak.performancetracker.context.Scope;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "format", havingValue = "json")
public class JsonDescriptor implements Descriptor {

    private static final String JSON_PATH = "./logs/performance.json";

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void describe(final Context context) {
        final File file = new File(JSON_PATH);
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
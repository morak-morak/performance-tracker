package com.morak.performancetracker.description;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.morak.performancetracker.context.Context;
import com.morak.performancetracker.context.Scope;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "com.morak.performance-tracker.format", havingValue = "json")
public class JsonDescriptor implements Descriptor {

    private static final String JSON_FORMAT = ".json";

    private final String filePath;
    private final ObjectMapper objectMapper;

    public JsonDescriptor(@Value("${com.morak.performance-tracker.logs.path:./logs/}") String filePath,
                          ObjectMapper objectMapper) {
        this.filePath = filePath;
        this.objectMapper = objectMapper;
    }

    @Override
    public void describe(Context context) {
        File file = new File(filePath + createFileName(context) + JSON_FORMAT);
        try (FileWriter fileWriter = new FileWriter(file, true);
             SequenceWriter seqWriter = objectMapper.writer().writeValuesAsArray(fileWriter)) {
            writeToFile(context, seqWriter);
        } catch (IOException e) {
            throw new RuntimeException("I/O error writing context");
        }
    }

    private String createFileName(Context context) {
        System.out.println(getTargetClassName(context) + getNowDate());
        return getTargetClassName(context) + getNowDate();
    }

    private String getTargetClassName(Context context) {
        String[] filePaths = context.getName()
                .split("\\.");
        int length = filePaths.length;
        return filePaths[length - 1];
    }

    private String getNowDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private void writeToFile(Context context, SequenceWriter seqWriter) throws IOException {
        for (Scope scope : context.getScopes()) {
            seqWriter.write(scope);
        }
    }
}

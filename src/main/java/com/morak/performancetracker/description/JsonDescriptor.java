package com.morak.performancetracker.description;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.morak.performancetracker.ContextType;
import com.morak.performancetracker.configuration.DescriptorProperties;
import com.morak.performancetracker.context.Root;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "com.morak.performance-tracker.format", havingValue = "json")
public class JsonDescriptor implements Descriptor {

    private static final String JSON_FORMAT = ".json";

    private final String path;
    private final String file;
    private final ObjectMapper objectMapper;

    public JsonDescriptor(DescriptorProperties descriptorProperties,
                          ObjectMapper objectMapper) {
        this.path = descriptorProperties.getPath();
        this.file = descriptorProperties.getFile();
        this.objectMapper = objectMapper;
    }

    @Override
    public void describe(Root root, ContextType contextType) {
        File jsonFile = new File(path + createFileName(contextType) + JSON_FORMAT);
        try (FileWriter fileWriter = new FileWriter(jsonFile, true);
             SequenceWriter seqWriter = objectMapper.writer().writeValuesAsArray(fileWriter)) {
            seqWriter.write(root);
        } catch (IOException e) {
            throw new RuntimeException("I/O error writing context");
        }
    }

    private String createFileName(ContextType contextType) {
        return file + "-" + contextType.name().toLowerCase(Locale.ROOT) + "-" + getNowDate();
    }

    private String getNowDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}

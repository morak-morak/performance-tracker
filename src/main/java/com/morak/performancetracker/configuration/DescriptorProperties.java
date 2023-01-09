package com.morak.performancetracker.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConstructorBinding
@ConfigurationProperties(prefix = "com.morak.performance-tracker.logs")
public class DescriptorProperties {

    private final String path;
    private final String file;

    public DescriptorProperties(@DefaultValue("./logs/") String path,
                                @DefaultValue("performance") String file) {
        this.path = path;
        this.file = file;
    }

    public String getPath() {
        return path;
    }

    public String getFile() {
        return file;
    }
}

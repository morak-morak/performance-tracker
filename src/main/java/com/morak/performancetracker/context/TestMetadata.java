package com.morak.performancetracker.context;

import com.morak.performancetracker.utils.StringUtils;

import java.util.Objects;

public class TestMetadata {

    public static final TestMetadata ROOT = new TestMetadata("ROOT");

    private String name;

    public TestMetadata(String name) {
        this.name = name;
    }

    public static TestMetadata of(String... names) {
        StringBuilder builder = new StringBuilder("ROOT");
        for (String name : names) {
            builder.append("@")
                    .append(name);
        }
        return new TestMetadata(builder.toString());
    }

    public TestMetadata parent() {
        return new TestMetadata(StringUtils.substringUntil(this.name, '@'));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestMetadata that = (TestMetadata) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        String self = StringUtils.substringFrom(this.name, '@');
        return "TestMetadata{" +
                "name='" + self + '\'' +
                '}';
    }
}

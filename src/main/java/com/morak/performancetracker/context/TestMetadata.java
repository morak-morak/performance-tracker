package com.morak.performancetracker.context;

import java.util.Objects;

public class TestMetadata {

    public static final TestMetadata ROOT = new TestMetadata("ROOT", "ROOT");

    private String className;
    private String methodName;

    public TestMetadata(String className, String methodName) {
        this.className = className;
        this.methodName = methodName;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestMetadata that = (TestMetadata) o;
        return Objects.equals(className, that.className) && Objects.equals(methodName, that.methodName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(className, methodName);
    }

    @Override
    public String toString() {
        return "TestMetadata{" +
                "className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                '}';
    }
}

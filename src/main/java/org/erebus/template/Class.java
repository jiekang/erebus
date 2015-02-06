package org.erebus.template;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class Class {
    private final String packageName;
    private final String className;

    private final Set<String> importList;
    private final Set<String> fieldList;

    private final Set<Method> methodList;

    public Class(String packageName, String className) {
        this(packageName, className,
                new HashSet<>(),
                new HashSet<>(),
                new HashSet<>());
    }

    public Class(String packageName, String className, Set<String> importList, Set<String> fieldList, Set<Method> methodList) {
        this.packageName = packageName;
        this.className = className;
        this.importList = importList;
        this.fieldList = fieldList;
        this.methodList = methodList;
    }

    public void addImport(String importString) {
        this.importList.add(importString);
    }

    public void addField(String field) {
        this.fieldList.add(field);
    }

    public void addMethod(Method method) {
        this.methodList.add(method);
    }

    public void createClassFile(File outputDir) {

    }

    public String createClassString() {
        return "";
    }
}

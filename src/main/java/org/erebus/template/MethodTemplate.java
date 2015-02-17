package org.erebus.template;

import java.util.HashSet;
import java.util.Set;

public class MethodTemplate {
    private final String methodName;

    private final boolean isStatic;

    private Set<String> argumentList;

    private Set<MethodTemplate> callList;

    public MethodTemplate(String methodName, boolean isStatic) {
        this(methodName, isStatic,
                new HashSet<>(),
                new HashSet<>());
    }

    public MethodTemplate(String methodName, boolean isStatic, Set<String> argumentList, Set<MethodTemplate> callList) {
        this.methodName = methodName;
        this.isStatic = isStatic;
        this.argumentList = argumentList;
        this.callList = callList;
    }

    private String createMethodString() {
        return "";
    }

}

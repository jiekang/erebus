package org.erebus.template;

import java.util.HashSet;
import java.util.Set;

import org.erebus.config.MethodConfig;

public class MethodTemplate {
    private final String fullClassName;

    private final String methodName;

    private final boolean isStaticMethod;

    private final MethodConfig config;

    private Set<String> argumentList;

    private Set<MethodTemplate> callList;

    public MethodTemplate(String fullClassName, String methodName, boolean isStatic, MethodConfig config) {
        this(fullClassName, methodName, isStatic, config,
                new HashSet<>(),
                new HashSet<>());
    }

    public MethodTemplate(String fullClassName, String methodName, boolean isStaticMethod, MethodConfig config,
                          Set<String> argumentList, Set<MethodTemplate> callList) {
        this.fullClassName = fullClassName;
        this.methodName = methodName;
        this.isStaticMethod = isStaticMethod;
        this.config = config;
        this.argumentList = argumentList;
        this.callList = callList;
    }

    public boolean isStatic() {
        return this.isStaticMethod;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public String getFullClassName() {
        return this.fullClassName;
    }

    public void addArgument(String arg) {
        this.argumentList.add(arg);
    }

    public String createMethodString() {
        String template = TemplateFactory.getMethodTemplate();

        String methodSignature = createMethodSignature();
        template = template.replaceFirst("@methodSignature", methodSignature);

        template = template.replaceFirst("@methodName", this.methodName);

        String arguments = createArguments();
        template = template.replaceFirst("@arguments", arguments);

        String methodCalls = createMethodCalls();
        template = template.replaceFirst("@methodCalls", methodCalls);

        return template;
    }

    private String createMethodSignature() {
        String staticString = " ";
        if (this.isStaticMethod) {
            staticString = " static ";
        }

        return "public" + staticString + "void";
    }

    private String createArguments() {
        String argumentString = "";
        String[] argList = argumentList.toArray(new String[0]);
        if (argList.length > 1) {
            for (int i = 0; i < argList.length - 1; i++) {
                argumentString = argumentString + argList[i] + ", ";
            }
            argumentString = argumentString + argList[argList.length];
        } else {
            argumentString = argumentString + argList[0];
        }

        return argumentString;
    }

    private String createMethodCalls() {
        String methodCalls = "";
        for (MethodTemplate method : callList) {
            String call = "";
            if (method.isStatic()) {
                call = method.getFullClassName() + "." + method.getMethodName() + "();";
            } else {
                call = call + method.getFullClassName() + " gen" + method.getMethodName() + " = new " + method.getFullClassName() + "();" + System.lineSeparator();
                call = call + "gen" + method.getMethodName() + "." + method.getMethodName() + "();" + System.lineSeparator();
            }
            methodCalls = call + System.lineSeparator();
        }

        return methodCalls;
    }

}

package org.erebus.template;

import java.util.HashSet;
import java.util.Set;

import org.erebus.config.MethodConfig;

public class DefaultMethodTemplate implements MethodTemplate {
    private final String fullClassName;

    private final String methodName;

    private final boolean isStaticMethod;

    private final MethodConfig config;

    private final Set<String> argumentList;

    private final Set<MethodTemplate> callList;

    public DefaultMethodTemplate(String fullClassName, String methodName, boolean isStatic, MethodConfig config) {
        this(fullClassName, methodName, isStatic, config,
                new HashSet<>(),
                new HashSet<>());
    }

    public DefaultMethodTemplate(String fullClassName, String methodName, boolean isStaticMethod, MethodConfig config,
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

    public void addCall(DefaultMethodTemplate call) {
        this.callList.add(call);
    }

    @Override
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
        } else if (argList.length == 1) {
            argumentString = argumentString + argList[0];
        }

        return argumentString;
    }

    private String createMethodCalls() {
        String methodCalls = "";
        for (MethodTemplate method : callList) {
            methodCalls = methodCalls + method.getMethodCallString() + System.lineSeparator();
        }

        return methodCalls;
    }

    @Override
    public String getMethodCallString() {
        String call = "";
        if (isStatic()) {
            call = getFullClassName() + "." + getMethodName() + "();";
        } else {
            call = call + getFullClassName() + " gen" + getMethodName() + " = new " + getFullClassName() + "();" + System.lineSeparator();
            call = call + "gen" + getMethodName() + "." + getMethodName() + "();" + System.lineSeparator();
        }
        return call;
    }

}

package org.erebus.template;

import java.util.HashSet;
import java.util.Set;

import org.erebus.config.MethodConfig;

public class MainMethodTemplate implements MethodTemplate {
    private final String fullClassName;

    private final String methodName = "main";

    private final boolean isStaticMethod = true;

    private final Set<MethodTemplate> callList;

    public MainMethodTemplate(String fullClassName) {
        this(fullClassName, new HashSet<>());
    }

    public MainMethodTemplate(String fullClassName, Set<MethodTemplate> callList) {
        this.fullClassName = fullClassName;
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

    public void addCall(MethodTemplate call) {
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
        return "public static void";
    }

    private String createArguments() {
        return "String[] args";
    }

    private String createMethodCalls() {
        String methodCalls =
                "int callCount = 0;" + System.lineSeparator() +
                "try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {" + System.lineSeparator() +
                "while(br.readLine()==null){" + System.lineSeparator();

        for (MethodTemplate method : callList) {
            methodCalls = methodCalls + method.getMethodCallString() + System.lineSeparator();
        }

        methodCalls = methodCalls +
                "}" + System.lineSeparator() +
                "} catch(IOException e) {" + System.lineSeparator() +
                "e.printStackTrace();" + System.lineSeparator() +
                "}";

        return methodCalls;
    }

    @Override
    public String getMethodCallString() {
        String call = "";
        if (isStatic()) {
            call = getFullClassName() + "." + getMethodName() + "(0);";
        } else {
            call = call + getFullClassName() + " gen" + getFullClassName().replaceAll("\\.", "") + getMethodName() + " = new " + getFullClassName() + "();" + System.lineSeparator();
            call = call + "gen" + getFullClassName().replaceAll("\\.", "") + getMethodName() + "." + getMethodName() + "(0);" + System.lineSeparator();
        }
        return call;
    }
}

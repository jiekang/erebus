/*
    Erebus Alpha
    Copyright (C) 2015, 2020  Jie Kang

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package org.erebus.template;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
        String staticString = " ";
        if (this.isStaticMethod) {
            staticString = " static ";
        }

        return "public" + staticString + "void";
    }

    private String createArguments() {
        String argumentString = "int callCount, final AtomicBoolean atomicBoolean";


        String[] argList = argumentList.toArray(new String[0]);
        if (argList.length > 0) {
            argumentString = argumentString + ", ";
        }
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
        String methodCalls =
                "if (callCount > 10 || !atomicBoolean.get()) { return; }" + System.lineSeparator() +
                "int j = callCount  + 1;" + System.lineSeparator();

        if (config.isFileOperationsEnabled()) {
            methodCalls = methodCalls + getFileIOString() + System.lineSeparator();
        }
        for (MethodTemplate method : callList) {
            if (config.isThreadingEnabled() && config.getThreadChance().getChance()) {
                String threadName = getMethodName() + UUID.randomUUID().toString().replaceAll("-", "");
                methodCalls = methodCalls +
                        "Thread " + threadName + " = new Thread(new Runnable() {" + System.lineSeparator() +
                        "        @Override" + System.lineSeparator() +
                        "        public void run() {" + System.lineSeparator() +
                        "            int i = callCount + 1;" + System.lineSeparator() +
                        getMethodCallString("i, atomicBoolean") +
                        "        }" + System.lineSeparator() +
                        "    });" + System.lineSeparator() +
                        threadName + ".start();" + System.lineSeparator();
                if (!config.getThreadChance().getChance()) {
                    methodCalls = methodCalls +
                            "try {" + System.lineSeparator() +
                            threadName + ".join();" + System.lineSeparator() +
                            "} catch (InterruptedException e) {" + System.lineSeparator() +
                            "e.printStackTrace();" + System.lineSeparator() +
                            "}" + System.lineSeparator();
                }
            } else {
                methodCalls = methodCalls +
                        method.getMethodCallString("j, atomicBoolean") + System.lineSeparator();
            }
        }

        return methodCalls;
    }

    @Override
    public String getMethodCallString(String arguments) {
        String call = "";
        if (isStatic()) {
            call = getFullClassName() + "." + getMethodName() + "(" + arguments + ");";
        } else {
            call = call + getFullClassName() + " gen" + getFullClassName().replaceAll("\\.", "") + getMethodName() + " = new " + getFullClassName() + "();" + System.lineSeparator();
            call = call + "gen" + getFullClassName().replaceAll("\\.", "") + getMethodName() + "." + getMethodName() + "(" + arguments + ");" + System.lineSeparator();
        }
        return call;
    }

    public String getFileIOString() {
        String fileIO = "try {" + System.lineSeparator() +
                "File f = new File(\"" + this.methodName + "\");" + System.lineSeparator() +
                "f.createNewFile();" + System.lineSeparator() +
                "f.deleteOnExit();" + System.lineSeparator() +
                "String s = \"\";" + System.lineSeparator() +
                "for (int i = 0; i < 10000; i++) {" + System.lineSeparator() +
                "s = s + \"input\";" + System.lineSeparator() +
                "}" + System.lineSeparator() +
                "Files.write(f.toPath(), s.getBytes());" + System.lineSeparator() +
                "} catch (IOException e) {" + System.lineSeparator() +
                "}";
        return fileIO;
    }
}

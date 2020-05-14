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
                "final AtomicBoolean atomicBoolean = new AtomicBoolean(true);" + System.lineSeparator() +
                "Thread t = new Thread(new Runnable() {" + System.lineSeparator() +
                "@Override" + System.lineSeparator() +
                "public void run() {" + System.lineSeparator() +
                "while (atomicBoolean.get()) {" + System.lineSeparator() +
                "int callCount = 0;" + System.lineSeparator();

        for (MethodTemplate method : callList) {
            methodCalls = methodCalls + method.getMethodCallString("++callCount, atomicBoolean") + System.lineSeparator();
        }

        methodCalls = methodCalls +
                "}" + System.lineSeparator() +
                "}" + System.lineSeparator() +
                "});" + System.lineSeparator() +
                "t.start();" + System.lineSeparator() +
                "try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {" + System.lineSeparator() +
                "while (br.readLine() == null) {" + System.lineSeparator() +
                "}" + System.lineSeparator() +
                "atomicBoolean.set(false);" + System.lineSeparator() +
                "} catch (IOException e) {" + System.lineSeparator() +
                "e.printStackTrace();" + System.lineSeparator() +
                "}";

        return methodCalls;
    }

    @Override
    public String getMethodCallString(String arguments) {
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

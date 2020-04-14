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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

public class ClassTemplate {
    private final String packageName;
    private final String className;

    private final Set<String> importList;
    private final Set<String> fieldList;

    private final Set<MethodTemplate> methodList;


    public ClassTemplate(String packageName, String className) {
        this(packageName, className,
                new HashSet<>(),
                new HashSet<>(),
                new HashSet<>());
    }

    public ClassTemplate(String packageName, String className, Set<String> importList, Set<String> fieldList, Set<MethodTemplate> methodList) {
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

    public void addMethod(MethodTemplate method) {
        this.methodList.add(method);
    }

    public Set<MethodTemplate> getMethods() {
        return this.methodList;
    }

    public void createClassFile(File outputDir) throws IOException {
        File packageFile = createPackageDirectory(outputDir);

        File classFile = new File(packageFile, this.className + ".java");
        classFile.createNewFile();

        System.out.println("Writing file: " + classFile.getAbsolutePath());

        String classString = createClass();
        Files.write(classFile.toPath(), classString.getBytes());
    }

    private File createPackageDirectory(File outputDir) {
        String packageString = this.packageName.replaceAll("[.]", "/");
        File packageFile = new File(outputDir, packageString);
        packageFile.mkdirs();

        return packageFile;
    }

    private String createClass() {
        String template = TemplateFactory.getClassTemplate();

        template = template.replaceFirst("@packageName", this.packageName + ";");

        String importString = createImports();
        template = template.replaceFirst("@import", importString);

        template = template.replaceFirst("@className", this.className);

        String fieldString = createFields();
        template = template.replaceFirst("@field", fieldString);

        String methodString = createMethods();
        template = template.replaceFirst("@method", methodString);

        return template;
    }

    private String createImports() {
        String importString = "";
        for (String importName : importList) {
            importString = importString + "import " + importName + ";" + System.lineSeparator();
        }

        return importString;
    }

    private String createFields() {
        String fieldString = "";
        for (String fieldName : fieldList) {
            fieldString = fieldString + fieldName + System.lineSeparator();
        }

        return fieldString;
    }

    private String createMethods() {
        String methodString = "";
        for (MethodTemplate method : methodList) {
            methodString = methodString + method.createMethodString() + System.lineSeparator();
        }

        return methodString;
    }

    public String getFullClassName() {
        return this.packageName + "." + this.className;
    }
}

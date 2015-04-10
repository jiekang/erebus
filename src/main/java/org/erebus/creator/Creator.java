/*
    Erebus Alpha
    Copyright (C) 2015  Jie Kang

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

package org.erebus.creator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.erebus.config.Config;
import org.erebus.config.ConfigFactory;
import org.erebus.probability.Probability;
import org.erebus.range.Range;
import org.erebus.template.ClassTemplate;
import org.erebus.template.DefaultMethodTemplate;
import org.erebus.template.MainMethodTemplate;
import org.erebus.template.MethodTemplate;

public class Creator {
    private final Config config;

    private final List<ClassTemplate> classList = new ArrayList<>();

    private int numClasses = 0;

    public Creator(final File configFile) {
        config = ConfigFactory.createConfig(configFile);
    }

    public void create() {
        createMainClass();
        outputClassFiles();
    }

    private void createMainClass() {
        numClasses++;

        ClassTemplate mainClass = new ClassTemplate(config.getBasePackage() + ".main", "Main");

        MainMethodTemplate mainMethod = new MainMethodTemplate(mainClass.getFullClassName());
        mainClass.addMethod(mainMethod);
        mainClass.addImport("java.io.BufferedReader");
        mainClass.addImport("java.io.IOException");
        mainClass.addImport("java.io.InputStreamReader");
        mainClass.addImport("java.util.concurrent.atomic.AtomicBoolean");

        int numCalls = getNumCalls();
        for (int i = 0; i < numCalls; i++) {
            if (numClasses < config.getNumClasses()) {
                ClassTemplate c = createClass();
                for (MethodTemplate m : c.getMethods()) {
                    mainMethod.addCall(m);
                }
            } else {
                ClassTemplate c = getClassTemplate();
                for (MethodTemplate m : c.getMethods()) {
                    mainMethod.addCall(m);
                }
            }
        }

        classList.add(mainClass);
    }

    private ClassTemplate createClass() {
        numClasses++;
        ClassTemplate classTemplate = new ClassTemplate(config.getBasePackage() + ".random", "Random" + numClasses);
        classTemplate.addImport("java.io.File");
        classTemplate.addImport("java.io.IOException");
        classTemplate.addImport("java.nio.file.Files");
        classTemplate.addImport("java.util.concurrent.atomic.AtomicBoolean");


        classList.add(classTemplate);

        int numMethods = getNumMethods();
        Probability isStatic = new Probability(50);

        for (int i = 0; i < numMethods; i++) {
            DefaultMethodTemplate method = new DefaultMethodTemplate(classTemplate.getFullClassName(), "method" + i, isStatic.getChance(), config.getMethodConfig());
            classTemplate.addMethod(method);

            int numCalls = getNumCalls();
            for (int j = 0; j < numCalls; j++) {
                ClassTemplate c;
                if (numClasses < config.getNumClasses()) {
                    c = createClass();
                } else {
                    c = getClassTemplate();
                }
                for (MethodTemplate m : c.getMethods()) {
                    method.addCall(m);
                }
            }
        }

        return classTemplate;
    }

    private int getNumMethods() {
        return config.getMethodRange().getNumber();
    }

    private int getNumCalls() {
        return config.getMethodConfig().getCallRange().getNumber();
    }

    private ClassTemplate getClassTemplate() {
        Range r = new Range(0, classList.size() - 1);
        return classList.get(r.getNumber());
    }

    private void outputClassFiles() {
        for (ClassTemplate classTemplate : classList) {
            try {
                classTemplate.createClassFile(config.getOutputDir());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

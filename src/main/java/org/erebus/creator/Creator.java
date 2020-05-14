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

package org.erebus.creator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

    // TODO
    private int numClasses = 0;

    public Creator() {
        config = ConfigFactory.createDefaultConfig();
    }

    public Creator(final File configFile) {
        config = ConfigFactory.createConfig(configFile.toPath());
    }

    // TODO
    public void create() {
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

        for (ClassTemplate classTemplate : classList) {
            try {
                classTemplate.createClassFile(config.getOutputDir());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File build = new File(config.getOutputDir(), "build.sh");
        try {
            build.createNewFile();

            String buildString = "#!/bin/bash" + System.lineSeparator() + "find . -name *.java | xargs javac";

            System.out.println("Writing file: " + build.getAbsolutePath());
            Files.write(build.toPath(), buildString.getBytes());
            build.setExecutable(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File run = new File(config.getOutputDir(), "run.sh");
        try {
            run.createNewFile();

            String runString = "#!/bin/bash" + System.lineSeparator() + "java " + config.getBasePackage()
                    + ".main.Main";

            System.out.println("Writing file: " + run.getAbsolutePath());
            Files.write(run.toPath(), runString.getBytes());
            run.setExecutable(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO
private ClassTemplate createClass() {
	numClasses++;
	ClassTemplate classTemplate = new ClassTemplate(config.getBasePackage() + ".random", "Random" + numClasses);
	if (config.getMethodConfig().isFileOperationsEnabled()) {
		classTemplate.addImport("java.io.File");
		classTemplate.addImport("java.io.IOException");
		classTemplate.addImport("java.nio.file.Files");
	}
	classTemplate.addImport("java.util.concurrent.atomic.AtomicBoolean");

	classList.add(classTemplate);

	int numMethods = getNumMethods();
	Probability isStatic = new Probability(50);

	for (int i = 0; i < numMethods; i++) {
		DefaultMethodTemplate method = new DefaultMethodTemplate(classTemplate.getFullClassName(), "method" + i,
				isStatic.getChance(), config.getMethodConfig());
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

    // TODO
    private int getNumMethods() {
        return config.getMethodRange().getNumber();
    }

    // TODO
    private int getNumCalls() {
        return config.getMethodConfig().getCallRange().getNumber();
    }

    // TODO
    private ClassTemplate getClassTemplate() {
        Range r = new Range(0, classList.size() - 1);
        return classList.get(r.getNumber());
    }
}

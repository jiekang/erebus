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
import org.erebus.template.ClassTemplate;
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

        for (ClassTemplate classTemplate : classList) {
            try {
                classTemplate.createClassFile(config.getOutputDir());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createMainClass() {
        numClasses++;

        ClassTemplate mainClass = new ClassTemplate(config.getBasePackage() + ".main", "Main");

        MethodTemplate mainMethod = new MethodTemplate(mainClass.getFullClassName(), "main", true, config.getMethodConfig());
        mainMethod.addArgument("String[] args");

        mainClass.addMethod(mainMethod);

        classList.add(mainClass);
    }

    private int getNumMethods() {
        return config.getMethodRange().getNumber();
    }

    private void createClass() {

    }
}

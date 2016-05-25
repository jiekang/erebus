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

package org.erebus.config;

import java.io.File;

import org.erebus.range.Range;


public class Config {

    /**
     * number of classes to create
     */
    private final int numClasses;

    /**
     * min/max number of methods per class
     */
    private final Range methodRange;

    /**
     * base package
     */
    private final String basePackage;

    /**
     * Output directory of classes
     */
    private final File outputDir;

    private final MethodConfig methodConfig;

    protected Config() {
        numClasses = ConfigProperties.NumberProperties.NUMBER_OF_CLASSES.value;
        methodRange = ConfigProperties.RangeProperties.NUM_METHOD_RANGE.value;

        basePackage = ConfigProperties.StringProperties.BASE_PACKAGE.value;

        outputDir = new File(ConfigProperties.StringProperties.OUTPUT_DIRECTORY.value);
        outputDir.mkdirs();

        methodConfig = new MethodConfig();
    }

    public int getNumClasses() {
        return this.numClasses;
    }

    public Range getMethodRange() {
        return this.methodRange;
    }

    public String getBasePackage() {
        return this.basePackage;
    }

    public File getOutputDir() {
        return this.outputDir;
    }

    public MethodConfig getMethodConfig() {
        return this.methodConfig;
    }
}

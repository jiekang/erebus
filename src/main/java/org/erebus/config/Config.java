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
     * min/max number of calls per method
     */
    private final Range callRange;

    /**
     * whether or not to write code that uses threads
     */
    private final boolean threadingEnabled;

    /**
     * whether or not to write code that sleeps in methods
     */
    private final boolean sleepingEnabled;

    /**
     * min/max sleep time in milliseconds
     */
    private final Range sleepRange;

    /**
     * base package
     */
    private final String basePackage;

    protected Config() {
        numClasses = 100;
        methodRange = new Range(1, 5);
        callRange = new Range(1, 3);
        threadingEnabled = true;

        sleepingEnabled = true;
        sleepRange = new Range(100, 500);

        basePackage = "org.erebus.generated";
    }

    public int getNumClasses() {
        return this.numClasses;
    }

    public Range getMethodRange() {
        return this.methodRange;
    }

    public Range getCallRange() {
        return this.callRange;
    }

    public boolean getThreadingEnabled() {
        return this.threadingEnabled;
    }

    public boolean isSleepingEnabled() {
        return this.sleepingEnabled;
    }

    public Range getSleepRange() {
        return this.sleepRange;
    }

    public String getBasePackage() {
        return this.basePackage;
    }
}

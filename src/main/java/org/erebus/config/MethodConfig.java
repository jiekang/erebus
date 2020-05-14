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

package org.erebus.config;

import org.erebus.probability.Probability;
import org.erebus.range.Range;

public class MethodConfig {

    /**
     * min/max number of calls per method
     */
    private final Range callRange;

    /**
     * whether or not to write code that uses threads
     */
    private final boolean threadingEnabled;

    /**
     * whether or not to write code that performs file operations
     */
    private final boolean fileOperationsEnabled;

    /**
     * probability of method creating a new thread
     */
    private final Probability threadChance;

    /**
     * whether or not to write code that sleeps in methods
     */
    private final boolean sleepingEnabled;

    /**
     * min/max sleep time in milliseconds
     */
    private final Range sleepRange;

    protected MethodConfig() {
        callRange = ConfigProperties.RangeProperties.NUM_METHOD_CALL_RANGE.value;

        threadingEnabled = ConfigProperties.BooleanProperties.ENABLE_THREADING.value;
        threadChance = new Probability(ConfigProperties.NumberProperties.THREAD_CREATION_PROBABILITY.value);

        fileOperationsEnabled = ConfigProperties.BooleanProperties.ENABLE_FILE_OPERATIONS.value;

        sleepingEnabled = ConfigProperties.BooleanProperties.ENABLE_SLEEPING.value;
        sleepRange = ConfigProperties.RangeProperties.SLEEP_RANGE.value;
    }

    public Range getCallRange() {
        return this.callRange;
    }

    public boolean isThreadingEnabled() {
        return this.threadingEnabled;
    }

    public boolean isFileOperationsEnabled() {
        return this.fileOperationsEnabled;
    }

    public Probability getThreadChance() {
        return this.threadChance;
    }

    public boolean isSleepingEnabled() {
        return this.sleepingEnabled;
    }

    public Range getSleepRange() {
        return this.sleepRange;
    }
}

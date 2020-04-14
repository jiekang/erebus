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

import org.erebus.range.Range;

public class ConfigProperties {
    public static enum NumberProperties {
        NUMBER_OF_CLASSES(3),
        THREAD_CREATION_PROBABILITY(50),
        ;

        public int value;

        NumberProperties(final int defaultValue) {
            this.value = defaultValue;
        }
    }

    public static enum RangeProperties{
        NUM_METHOD_RANGE(1, 5),
        NUM_METHOD_CALL_RANGE(1, 5),
        SLEEP_RANGE(100, 500),
        ;

        public Range value;

        RangeProperties(final int defaultMinValue, final int defaultMaxValue) {
            this.value = new Range(defaultMinValue, defaultMaxValue);
        }
    }

    public static enum StringProperties {
        BASE_PACKAGE("org.erebus.generated"),
        OUTPUT_DIRECTORY("output"),
        ;

        public String value;

        StringProperties(final String defaultValue) {
            this.value = defaultValue;
        }

    }

    public static enum BooleanProperties {
        ENABLE_THREADING(true),
        ENABLE_FILE_OPERATIONS(false),
        ENABLE_SLEEPING(true),
        ;

        public boolean value;

        BooleanProperties(final boolean defaultValue) {
            this.value = defaultValue;
        }

    }

}

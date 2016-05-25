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

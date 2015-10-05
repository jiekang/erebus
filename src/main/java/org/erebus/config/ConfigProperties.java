package org.erebus.config;

public class ConfigProperties {
    public static enum NumberProperties {
        NUMBER_OF_CLASSES(3),
        NUMBER_OF_METHODS_MAXIMUM(5),
        NUMBER_OF_METHODS_MINIMUM(1),
        NUMBER_OF_CALLS_MAXIMUM(5),
        NUMBER_OF_CALLS_MINIMUM(1),
        THREAD_CREATION_PROBABILITY(50),
        SLEEP_TIME_MAXIMUM(500),
        SLEEP_TIME_MINIMUM(100),
        ;

        private final int defaultValue;

        private NumberProperties(final int defaultValue) {
            this.defaultValue = defaultValue;
        }

        public int getDefaultValue() {
            return defaultValue;
        }
    }

    public static enum StringProperties {
        BASE_PACKAGE("org.erebus.generated"),
        OUTPUT_DIRECTORY("output"),
        ;

        private final String defaultValue;

        private StringProperties(final String defaultValue) {
            this.defaultValue = defaultValue;
        }

    }

    public static enum BooleanProperties {
        ENABLE_THREADING(true),
        ENABLE_FILE_OPERATIONS(false),
        ENABLE_SLEEPING(true),
        ;

        private final boolean defaultValue;

        private  BooleanProperties(final boolean defaultValue) {
            this.defaultValue = defaultValue;
        }

    }

}

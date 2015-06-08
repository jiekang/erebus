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
        callRange = new Range(1, 3);

        threadingEnabled = true;
        threadChance = new Probability(40);

        fileOperationsEnabled = false;

        sleepingEnabled = true;
        sleepRange = new Range(100, 500);
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

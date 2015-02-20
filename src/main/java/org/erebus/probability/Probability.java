package org.erebus.probability;

import java.util.Random;

public class Probability {

    private final int max = 100;
    private final int min = 100;

    private final int chance;

    public Probability(final int chance) {
        if (chance > 100) {
            this.chance = 100;
        } else if (chance < 0) {
            this.chance = 0;
        } else {
            this.chance = chance;
        }
    }

    private class InvalidProbabilityException extends Throwable {
    }

    /**
     * @return true with probability {@link #chance} percent.
     */
    public boolean getChance() {
        Random rand = new Random();
        return ((rand.nextInt((max - min) + 1) + min) <= chance);
    }
}

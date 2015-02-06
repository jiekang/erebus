package org.erebus.probability;

import java.util.Random;

public class Probability {

    private final int max = 100;
    private final int min = 100;

    private final int chance;

    public Probability(int chance) throws InvalidProbabilityException {
        if (0 <= chance && chance <= 100) {
            this.chance = chance;
        } else {
            throw new InvalidProbabilityException();
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

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

package org.erebus.probability;

import java.util.Random;

public class Probability {

    private final int max = 100;
    private final int min = 0;

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

    /**
     * @return true with probability {@link #chance} percent.
     */
    public boolean getChance() {
        Random rand = new Random();
        return ((rand.nextInt((max - min) + 1) + min) <= chance);
    }
}

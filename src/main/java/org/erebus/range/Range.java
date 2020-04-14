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

package org.erebus.range;

import java.util.Random;

public class Range {
    private final int min;
    private final int max;

    public Range(final int min, final int max) {
        this.min = min;
        this.max = max;
    }

    /**
     * @return a number within {@link #min} and {@link #max} inclusive
     */
    public int getNumber() {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}

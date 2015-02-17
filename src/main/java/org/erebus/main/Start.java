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


package org.erebus.main;

import java.io.File;

import org.erebus.creator.Creator;

public class Start {

    private static final String DEFAULT_CONFIG_LOCATION = "./erebus.conf";

    public static void main(String[] args) {
        String configLocation = DEFAULT_CONFIG_LOCATION;
        if (args.length > 1) {
            configLocation = args[1];
        }

        File configFile = new File(configLocation);

        if (configFile.exists() && configFile.canRead()) {
            System.out.println("Creating java files using config: " + configLocation);
            Creator c = new Creator(configFile);
            c.create();
        }
    }
}

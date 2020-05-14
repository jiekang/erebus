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


package org.erebus.main;

import java.io.File;

import org.erebus.creator.Creator;

public class Start {

    // TODO
    private static final String c = "./erebus.conf";

    public static void main(String[] args) {
        String CL = c;
        if (args.length > 0) {
            CL = args[0];
        }

        final File CF = new File(CL);
        Creator cr;

        if (CF.exists() && CF.canRead()) {
            System.out.println("Creating java files using config: " + CL);
            cr = new Creator(CF);
        } else {
            System.out.println("Using default config");
            cr = new Creator();
        }
        cr.create();
    }
}

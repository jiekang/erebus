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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ConfigFactory {

    public static Config createDefaultConfig() {
        return new Config();
    }

    public static Config createConfig(Path configFile) {
        Map<String, String> propertyMap = new HashMap<>();


        // TODO: split this into readable functions
        try {
            String file = new String(Files.readAllBytes(configFile));
            for (String line : file.split(System.lineSeparator())) {
                String[] property = line.split("=");
                propertyMap.put(property[0], property[1]);
            }

            for (ConfigProperties.NumberProperties p : ConfigProperties.NumberProperties.values()) {
                String property = propertyMap.get(p.toString());
                if (property != null) {
                    p.value = Integer.parseInt(property);
                }
            }

            for (ConfigProperties.RangeProperties p : ConfigProperties.RangeProperties.values()) {
                String property = propertyMap.get(p.toString());
                if (property != null) {
                    String[] range = property.split(",");
                    p.value = new Range(Integer.parseInt(range[0]),
                            Integer.parseInt(range[1]));
                }
            }

            for (ConfigProperties.StringProperties p : ConfigProperties.StringProperties.values()) {
                String property = propertyMap.get(p.toString());
                if (property != null) {
                    p.value = property;
                }
            }

            for (ConfigProperties.BooleanProperties p : ConfigProperties.BooleanProperties.values()) {
                String property = propertyMap.get(p.toString());
                if (property != null) {
                    if (0 == property.compareTo("true")) {
                        p.value = true;
                    } else if (0 == property.compareTo("false")) {
                        p.value = false;
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println(System.lineSeparator() + "Using default config.");
        }

        // TODO: Unclear how 'new Config() is affected by configuration when default config does the same thing'
        return new Config();
    }
}

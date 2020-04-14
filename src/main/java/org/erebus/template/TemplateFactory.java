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

package org.erebus.template;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class TemplateFactory {

    private static final Map<String, String> templateMap = new HashMap<>();

    public static String getClassTemplate() {
        String key = "classTemplate";
        if (templateMap.containsKey(key)) {
            return templateMap.get(key);
        } else {
            String template = loadTemplate("Class.template");
            templateMap.put(key, template);
            return template;
        }
    }

    public static String getMethodTemplate() {
        String key = "methodTemplate";
        if (templateMap.containsKey(key)) {
            return templateMap.get(key);
        } else {
            String template = loadTemplate("Method.template");
            templateMap.put(key, template);
            return template;
        }
    }

    private static String loadTemplate(String fileName) {
        final Map<String, String> env = new HashMap<>();
        env.put("create", "true");

        URL entryURL = TemplateFactory.class.getResource("/org/erebus/template/" + fileName);
        try {
            String[] entry = entryURL.toURI().toString().split("!");
            FileSystem entryFS;
            try {
                entryFS = FileSystems.newFileSystem(URI.create(entry[0]), env);
            } catch (Exception e) {
                entryFS = FileSystems.getFileSystem(URI.create(entry[0]));
            }
            Path entryPath = entryFS.getPath(entry[1]);

            String template = new String(Files.readAllBytes(entryPath));

            entryFS.close();

            return template;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return "";
    }
}

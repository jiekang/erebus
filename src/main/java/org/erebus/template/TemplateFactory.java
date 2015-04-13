package org.erebus.template;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

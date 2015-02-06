package org.erebus.template;

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

    private static String loadTemplate(String fileName) {
        return "";
    }
}

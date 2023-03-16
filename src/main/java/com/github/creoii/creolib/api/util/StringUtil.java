package com.github.creoii.creolib.api.util;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {
    /**
     * Capitalize every letter after any character matching {@param after}
     */
    public static String capitalizeAfterAll(String str, char after) {
        str = StringUtils.capitalize(str);
        StringBuilder builder = new StringBuilder();
        boolean capitalize = false;
        for (int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) == after) {
                builder.append(str.charAt(i));
                capitalize = true;
            } else if (capitalize) {
                builder.append(Character.toUpperCase(str.charAt(i)));
                capitalize = false;
            } else {
                builder.append(str.charAt(i));
            }
        }
        return builder.toString();
    }
}

package by.mjc.utils;

import org.apache.commons.lang3.math.NumberUtils;

public class StringUtils {
    private StringUtils() {
    }

    public static boolean isPositiveNumber(String str) {
        if (NumberUtils.isParsable(str)) {
            double parseNumber = Double.parseDouble(str);
            return parseNumber > 0;
        }
        return false;
    }
}

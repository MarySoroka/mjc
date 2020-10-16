package by.mjc.utils;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

class StringUtilsTest {
    @Test
    void isPositiveNumberTrue(){
        boolean isPositive = StringUtils.isPositiveNumber("-1");
        assertFalse(isPositive);
        isPositive = StringUtils.isPositiveNumber("1");
        assertTrue(isPositive);
        isPositive = StringUtils.isPositiveNumber("null");
        assertFalse(isPositive);
        isPositive = StringUtils.isPositiveNumber("n");
        assertFalse(isPositive);
    }

}
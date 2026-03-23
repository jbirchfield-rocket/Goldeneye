package com.goldeneye.constants;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("BoolEnum Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class BoolEnumTest {

    @Test
    void falseOrdinalIsZero() {
        assertEquals(0, BoolEnum.FALSE.ordinal());
    }

    @Test
    void trueOrdinalIsOne() {
        assertEquals(1, BoolEnum.TRUE.ordinal());
    }

    @Test
    void valuesReturnsAllEnumConstants() {
        BoolEnum[] values = BoolEnum.values();
        assertEquals(2, values.length);
        assertEquals(BoolEnum.FALSE, values[0]);
        assertEquals(BoolEnum.TRUE, values[1]);
    }

    @Test
    void valueOfReturnCorrectEnum() {
        assertEquals(BoolEnum.FALSE, BoolEnum.valueOf("FALSE"));
        assertEquals(BoolEnum.TRUE, BoolEnum.valueOf("TRUE"));
    }
}

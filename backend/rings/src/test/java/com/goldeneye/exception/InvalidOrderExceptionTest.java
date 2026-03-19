package com.goldeneye.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

/**
 *
 * @author scanales
 */
@DisplayName("Invalid Order Exception Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class InvalidOrderExceptionTest {

    @Test
    void noArgConstructorCreatesInstance() {
        InvalidOrderException ex = new InvalidOrderException();

        assertNotNull(ex);
        assertNull(ex.getMessage());
        assertTrue(ex instanceof RuntimeException);
    }

    @Test
    void messageConstructorSetsMessage() {
        InvalidOrderException ex = new InvalidOrderException("Order must contain at least one item.");

        assertEquals("Order must contain at least one item.", ex.getMessage());
        assertTrue(ex instanceof RuntimeException);
    }
}

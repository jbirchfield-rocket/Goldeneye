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
@DisplayName("Invalid Location Exception Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class InvalidLocationExceptionTest {

    @Test
    void noArgConstructorCreatesInstance() {
        InvalidLocationException ex = new InvalidLocationException();

        assertNotNull(ex);
        assertNull(ex.getMessage());
        assertTrue(ex instanceof RuntimeException);
    }

    @Test
    void messageConstructorSetsMessage() {
        InvalidLocationException ex = new InvalidLocationException("Invalid location.");

        assertEquals("Invalid location.", ex.getMessage());
        assertTrue(ex instanceof RuntimeException);
    }
}

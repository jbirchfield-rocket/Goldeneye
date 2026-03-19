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
@DisplayName("Resource Not Found Exception Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ResourceNotFoundExceptionTest {

    @Test
    void noArgConstructorCreatesInstance() {
        ResourceNotFoundException ex = new ResourceNotFoundException();

        assertNotNull(ex);
        assertNull(ex.getMessage());
        assertTrue(ex instanceof RuntimeException);
    }

    @Test
    void messageConstructorSetsMessage() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Resource not found.");

        assertEquals("Resource not found.", ex.getMessage());
        assertTrue(ex instanceof RuntimeException);
    }
}

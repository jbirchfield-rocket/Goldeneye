package com.goldeneye.exception;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

/**
 *
 * @author scanales
 */
@DisplayName("Error Response Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ErrorResponseTest {

    @Test
    void constructorInitializesAllFields() {
        ErrorResponse response = new ErrorResponse(404, "Not Found");

        assertEquals(404, response.getStatus());
        assertEquals("Not Found", response.getMessage());
        assertNotNull(response.getTimestamp());
    }

    @Test
    void setStatusUpdatesValue() {
        ErrorResponse response = new ErrorResponse(404, "Not Found");

        response.setStatus(500);

        assertEquals(500, response.getStatus());
    }

    @Test
    void setMessageUpdatesValue() {
        ErrorResponse response = new ErrorResponse(404, "Not Found");

        response.setMessage("Internal Server Error");

        assertEquals("Internal Server Error", response.getMessage());
    }

    @Test
    void setTimestampUpdatesValue() {
        ErrorResponse response = new ErrorResponse(404, "Not Found");
        LocalDateTime newTimestamp = LocalDateTime.of(2026, 3, 19, 12, 0);

        response.setTimestamp(newTimestamp);

        assertEquals(newTimestamp, response.getTimestamp());
    }
}

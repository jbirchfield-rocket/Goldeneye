package com.goldeneye.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author scanales
 */
@DisplayName("Global Exception Handler Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void handleResourceNotFoundReturns404() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Resource not found.");

        ResponseEntity<ErrorResponse> response = handler.handleResourceNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(404, response.getBody().getStatus());
        assertEquals("Resource not found.", response.getBody().getMessage());
    }

    @Test
    void handleInvalidOrderReturns400() {
        InvalidOrderException ex = new InvalidOrderException("Order must contain at least one item.");

        ResponseEntity<ErrorResponse> response = handler.handleInvalidOrder(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().getStatus());
        assertEquals("Order must contain at least one item.", response.getBody().getMessage());
    }

    @Test
    void handleInvalidLocationReturns400() {
        InvalidLocationException ex = new InvalidLocationException("Invalid location.");

        ResponseEntity<ErrorResponse> response = handler.handleInvalidLocation(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().getStatus());
        assertEquals("Invalid location.", response.getBody().getMessage());
    }

    @Test
    void handleIllegalArgumentReturns400() {
        IllegalArgumentException ex = new IllegalArgumentException("Invalid argument.");

        ResponseEntity<ErrorResponse> response = handler.handleIllegalArgument(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().getStatus());
        assertEquals("Invalid argument.", response.getBody().getMessage());
    }

    @Test
    void handleGeneralExceptionReturns500() {
        Exception ex = new Exception("Something went wrong.");

        ResponseEntity<ErrorResponse> response = handler.handleGeneral(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(500, response.getBody().getStatus());
        assertEquals("An unexpected error occurred", response.getBody().getMessage());
    }
}

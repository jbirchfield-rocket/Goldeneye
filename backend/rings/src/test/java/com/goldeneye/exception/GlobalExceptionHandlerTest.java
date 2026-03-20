package com.goldeneye.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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

    @Test
    void handleTypeMismatchReturns400() {
        MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
        when(ex.getValue()).thenReturn("abc");
        when(ex.getName()).thenReturn("id");
        when((Class<?>) ex.getRequiredType()).thenReturn((Class) Integer.class);

        ResponseEntity<ErrorResponse> response = handler.handleTypeMismatch(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().getStatus());
        assertEquals("Invalid value 'abc' for parameter 'id'. Expected type: Integer.", response.getBody().getMessage());
    }

    @Test
    void handleTypeMismatchReturns400WhenRequiredTypeIsNull() {
        MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
        when(ex.getValue()).thenReturn("abc");
        when(ex.getName()).thenReturn("id");
        when(ex.getRequiredType()).thenReturn(null);

        ResponseEntity<ErrorResponse> response = handler.handleTypeMismatch(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().getStatus());
        assertEquals("Invalid value 'abc' for parameter 'id'. Expected type: unknown.", response.getBody().getMessage());
    }

    @Test
    void handleMessageNotReadableReturns400() {
        HttpMessageNotReadableException ex = mock(HttpMessageNotReadableException.class);

        ResponseEntity<ErrorResponse> response = handler.handleMessageNotReadable(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().getStatus());
        assertEquals("Malformed or missing request body.", response.getBody().getMessage());
    }
}

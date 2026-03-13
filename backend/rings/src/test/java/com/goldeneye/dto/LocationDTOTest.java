/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.goldeneye.dto;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
 
/**
 *
 * @author scanales
 */

@DisplayName("Location DTO Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class LocationDTOTest {

    @Test
    void constructorInitializesAllFields() {
        // LocationDTO: CustID, Street, City, State, Zip
        LocationDTO locationDto = new LocationDTO(2, "999 Test St", "Testville", "TX", "99999-9999");
        assertEquals(2, locationDto.getCustId());
        assertEquals("999 Test St", locationDto.getStreet());
        assertEquals("Testville", locationDto.getCity());
        assertEquals("TX", locationDto.getState());
        assertEquals("99999-9999", locationDto.getZip());
    }

    @Test
    void constructorDoesNotAllowNullFields() {
        // LocationDTO: CustID, Street, City, State, Zip
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new LocationDTO(null, "999 Test St", "Testville", "TX", "99999-9999")
        );
    }

    @Test
    void constructorDoesNotAllowEmptyFields() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new LocationDTO(2, "", "Testville", "TX", "99999-9999")
        );
    }

    @Test
    void constructorDoesNotAllowInvalidValues() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new LocationDTO(-1, "999 Test St", "Testville", "TX", "99999-999")
        );
    }
}
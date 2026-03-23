/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.goldeneye.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
        // LocationDTO: LocationID, CustID, Street, City, State, Zip
        LocationDTO locationDto = new LocationDTO(1, 2, "999 Test St", "Testville", "TX", "99999-9999");
        assertEquals(1, locationDto.getLocId());
        assertEquals(2, locationDto.getCustId());
        assertEquals("999 Test St", locationDto.getStreet());
        assertEquals("Testville", locationDto.getCity());
        assertEquals("TX", locationDto.getState());
        assertEquals("99999-9999", locationDto.getZip());
    }

    @Test
    void noArgConstructorCreatesInstance() {
        LocationDTO locationDto = new LocationDTO();
        assertNotNull(locationDto);
        assertNull(locationDto.getLocId());
        assertNull(locationDto.getCustId());
        assertNull(locationDto.getStreet());
        assertNull(locationDto.getCity());
        assertNull(locationDto.getState());
        assertNull(locationDto.getZip());
    }

    @Test
    void settersUpdateFields() {
        LocationDTO locationDto = new LocationDTO();
        locationDto.setLocId(5);
        locationDto.setCustId(10);
        locationDto.setStreet("123 New St");
        locationDto.setCity("Austin");
        locationDto.setState("TX");
        locationDto.setZip("78701");

        assertEquals(5, locationDto.getLocId());
        assertEquals(10, locationDto.getCustId());
        assertEquals("123 New St", locationDto.getStreet());
        assertEquals("Austin", locationDto.getCity());
        assertEquals("TX", locationDto.getState());
        assertEquals("78701", locationDto.getZip());
    }
    
}
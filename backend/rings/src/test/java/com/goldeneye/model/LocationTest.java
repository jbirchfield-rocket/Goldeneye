/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.goldeneye.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
 
/**
 *
 * @author kwall
 */

@DisplayName("Location Entity Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class LocationTest {

    @Test
    public void defaultConstructor_createsInstance() {
        Location location = new Location();
        assertNull(location.getLocationId());
        assertNull(location.getStreet());
        assertNull(location.getCity());
        assertNull(location.getState());
        assertNull(location.getZip());
    }

    @Test
    public void constructor_initializesAllFields() {
        Location location = new Location(
                1, 101, "123 Main St", "Denver", "CO", "80202-1234"
        );

        assertEquals(1, location.getLocationId());
        assertEquals(101, location.getCustId());
        assertEquals("123 Main St", location.getStreet());
        assertEquals("Denver", location.getCity());
        assertEquals("CO", location.getState());
        assertEquals("80202-1234", location.getZip());
    }

    @Test
    public void setLocationId_updatesValue() {
        Location location = new Location(1, 101, "123 Main St", "Denver", "CO", "80202-1234");

        location.setLocationId(2);

        assertEquals(2, location.getLocationId());
    }

    @Test
    public void setCustId_updatesValue() {
        Location location = new Location(1, 101, "123 Main St", "Denver", "CO", "80202-1234");

        location.setCustId(202);

        assertEquals(202, location.getCustId());
    }

    @Test
    public void setStreet_updatesValue() {
        Location location = new Location(1, 101, "123 Main St", "Denver", "CO", "80202-1234");

        location.setStreet("456 Oak Ave");

        assertEquals("456 Oak Ave", location.getStreet());
    }

    @Test
    public void setCity_updatesValue() {
        Location location = new Location(1, 101, "123 Main St", "Denver", "CO", "80202-1234");

        location.setCity("Boulder");

        assertEquals("Boulder", location.getCity());
    }

    @Test
    public void setState_updatesValue() {
        Location location = new Location(1, 101, "123 Main St", "Denver", "CO", "80202-1234");

        location.setState("TX");

        assertEquals("TX", location.getState());
    }

    @Test
    public void setZip_updatesValue() {
        Location location = new Location(1, 101, "123 Main St", "Denver", "CO", "80202-1234");

        location.setZip("75001-5678");

        assertEquals("75001-5678", location.getZip());
    }

    @Test
    public void setters_allowNullForReferenceFields() {
        Location location = new Location(1, 101, "123 Main St", "Denver", "CO", "80202-1234");

        location.setStreet(null);
        location.setCity(null);
        location.setState(null);

        assertNull(location.getStreet());
        assertNull(location.getCity());
        assertNull(location.getState());
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.goldeneye.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.goldeneye.dto.LocationDTO;
import com.goldeneye.rings.RingsApplication;
 
/**
 *
 * @author scanales
 */

@SpringBootTest(classes = RingsApplication.class)
@DisplayName("Location Service Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Tag("integration")
public class LocationServiceTests {

    @Autowired
    private LocationService locationService;

    // @Test
    // void getAllLocationsReturnsListOfLocationDTOs() {
    //     List<LocationDTO> locations = locationService.getAllLocations();
    //     assertNotNull(locations);
    //     assertFalse(locations.isEmpty());
    //     assertTrue(locations.stream().allMatch(l -> l instanceof LocationDTO));
    //     assertEquals(50, locations.size());
    //     assertTrue(locations.stream().anyMatch(l -> l.getCustId() == 2 && l.getStreet().equals("123 Main St.") && l.getCity().equals("Richmond") && l.getState().equals("VA")));
    //     assertTrue(locations.stream().anyMatch(l -> l.getCustId() == 12 && l.getStreet().equals("202 Maple Ln") && l.getCity().equals("Hicksville") && l.getState().equals("NY")));
    // }

    @Test
    void getLocationsByCustomerIdReturnsCorrectLocations() {
        List<LocationDTO> locations = locationService.getLocationsByCustId(2);
        assertNotNull(locations);
        assertFalse(locations.isEmpty());
        assertTrue(locations.stream().allMatch(l -> l instanceof LocationDTO));
        assertEquals(4, locations.size());
        assertTrue(locations.stream().anyMatch(l -> l.getLocID() == 1 && l.getCity().equals("Richmond")));
    }
}
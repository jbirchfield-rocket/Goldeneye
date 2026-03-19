/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.goldeneye.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.goldeneye.dto.LocationDTO;
import com.goldeneye.exception.InvalidLocationException;
import com.goldeneye.model.Location;
import com.goldeneye.repo.LocationRepo;

/**
 *
 * @author scanales and rskwall
 */

@DisplayName("Location Service Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
public class LocationServiceTests {

    @Mock
    private LocationRepo locationRepo;

    @InjectMocks
    private LocationService locationService;

    private Location location1;
    private Location location2;

    @BeforeEach
    void setUpLocations() {
        location1 = new Location(1, 2, "123 Main St.", "Richmond", "VA", "23220");
        location2 = new Location(2, 2, "456 Oak Ave.", "Richmond", "VA", "23221");
    }

    @Test
    void getLocationsByCustIdReturnsCorrectLocations() {
        when(locationRepo.findByCustId(2)).thenReturn(List.of(location1, location2));

        List<LocationDTO> locations = locationService.getLocationsByCustId(2);

        assertNotNull(locations);
        assertFalse(locations.isEmpty());
        assertEquals(2, locations.size());
        assertTrue(locations.stream().allMatch(l -> l instanceof LocationDTO));
        assertTrue(locations.stream().anyMatch(l -> l.getLocId() == 1 && l.getCity().equals("Richmond")));
        assertTrue(locations.stream().anyMatch(l -> l.getLocId() == 2 && l.getCity().equals("Richmond")));
    }

    @Test
    void getLocationByIdReturnsCorrectLocation() {
        when(locationRepo.findByLocId(1)).thenReturn(Optional.of(location1));

        LocationDTO location = locationService.getLocationById(1);

        assertNotNull(location);
        assertEquals(1, location.getLocId());
        assertEquals(2, location.getCustId());
        assertEquals("123 Main St.", location.getStreet());
        assertEquals("Richmond", location.getCity());
        assertEquals("VA", location.getState());
        assertEquals("23220", location.getZip());
    }

    @Test
    void getLocationByIdThrowsWhenLocationNotFound() {
        when(locationRepo.findByLocId(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> locationService.getLocationById(99));
    }

    @Test
    void addLocationReturnsNewLocationId() {
        LocationDTO newLocation = new LocationDTO(0, 2, "789 Pine Rd.", "Richmond", "VA", "23222");

        when(locationRepo.getLastGeneratedId()).thenReturn(3);

        int locId = locationService.addLocation(newLocation);

        assertEquals(3, locId);
        verify(locationRepo).insertLocation(2, "789 Pine Rd.", "Richmond", "VA", "23222");
    }

    @Test
    void addLocationThrowsWhenCustIdIsInvalid() {
        LocationDTO badCustId = new LocationDTO(0, 0, "789 Pine Rd.", "Richmond", "VA", "23222");

        assertThrows(InvalidLocationException.class, () -> locationService.addLocation(badCustId));
    }

    @Test
    void addLocationThrowsWhenStateCodeIsInvalid() {
        LocationDTO badState = new LocationDTO(0, 2, "789 Pine Rd.", "Richmond", "Virginia", "23222");

        assertThrows(InvalidLocationException.class, () -> locationService.addLocation(badState));
    }

    @Test
    void deleteLocationDeletesCorrectLocation() {
        locationService.deleteLocation(1);

        verify(locationRepo).deleteByLocId(1);
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.goldeneye.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.goldeneye.dto.LocationDTO;
import com.goldeneye.exception.InvalidLocationException;
import com.goldeneye.repo.LocationRepo;

/**
 *
 * @author dshelby
 */
@Service
public class LocationService {

    private static final Logger logger = LoggerFactory.getLogger(LocationService.class);

    private final LocationRepo locationRepo;

    public LocationService(LocationRepo locationRepo) {
        this.locationRepo = locationRepo;
    }

    public List<LocationDTO> getLocationsByCustId(int custId) {
        logger.info("Fetching locations for customer ID: {}", custId);
        List<LocationDTO> locations = locationRepo.findByCustId(custId)
            .stream()
            .map(c -> new LocationDTO(c.getLocationId(), c.getCustId(), c.getStreet(), c.getCity(), c.getState(), c.getZip()))
            .toList();
        logger.debug("Retrieved {} locations for customer ID: {}", locations.size(), custId);
        return locations;
    }

    public LocationDTO getLocationById(int locId) {
        logger.info("Fetching location with ID: {}", locId);
        return locationRepo.findByLocId(locId)
            .map(l -> new LocationDTO(l.getLocationId(), l.getCustId(), l.getStreet(), l.getCity(), l.getState(), l.getZip()))
            .orElseThrow(() -> {
                logger.warn("Location not found with ID: {}", locId);
                return new RuntimeException("Location not found: " + locId);
            });
    }

    public void deleteLocation(int locId) {
        logger.info("Deleting location with ID: {}", locId);
        locationRepo.deleteByLocId(locId);
        logger.debug("Location ID {} deleted successfully", locId);
    }
      
    @Transactional
    public int addLocation(LocationDTO location) {
        logger.info("Adding new location for customer ID: {}", location.getCustId());

        if (location.getCustId() == null || location.getCustId() < 1) {
            logger.warn("Invalid customer ID provided: {}", location.getCustId());
            throw new InvalidLocationException("Invalid customer ID: " + location.getCustId());
        }

        if (location.getState().length() != 2) {
            logger.warn("Invalid state code provided: {}", location.getState());
            throw new InvalidLocationException("Invalid state code (2 characters expected): " + location.getState());
        }

        locationRepo.insertLocation(location.getCustId(), location.getStreet(), location.getCity(), location.getState(), location.getZip());
        int newId = locationRepo.getLastGeneratedId();
        logger.info("Location created successfully with ID: {}", newId);
        return newId;
    }
}

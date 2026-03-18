/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.goldeneye.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goldeneye.dto.LocationDTO;
import com.goldeneye.repo.LocationRepo;

/**
 *
 * @author dshelby
 */
@Service
public class LocationService {

    private final LocationRepo locationRepo;

    public LocationService(LocationRepo locationRepo) {
        this.locationRepo = locationRepo;
    }

    public List<LocationDTO> getLocationsByCustId(int custId) {
        return locationRepo.findByCustId(custId)
            .stream()
            .map(c -> new LocationDTO(c.getLocationId(), c.getCustId(), c.getStreet(), c.getCity(), c.getState(), c.getZip()))
            .toList();
    }

    public LocationDTO getLocationById(int locId) {
        return locationRepo.findByLocId(locId)
            .map(l -> new LocationDTO(l.getLocationId(), l.getCustId(), l.getStreet(), l.getCity(), l.getState(), l.getZip()))
            .orElseThrow(() -> new RuntimeException("Location not found: " + locId));
    }

    public void deleteLocation(int locId) {
        locationRepo.deleteByLocId(locId);
    }
      
    @Transactional
    public int addLocation(LocationDTO location) {
        if (location.getCustId() == null || location.getCustId() < 1) {
            throw new IllegalArgumentException("Invalid customer ID: " + location.getCustId());
        }

        locationRepo.insertLocation(location.getCustId(), location.getStreet(), location.getCity(), location.getState(), location.getZip());
        return locationRepo.getLastGeneratedId();
    }
}

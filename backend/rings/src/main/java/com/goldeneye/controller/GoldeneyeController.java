/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.goldeneye.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goldeneye.dto.CustomerDTO;
import com.goldeneye.dto.LocationDTO;
import com.goldeneye.dto.StoneDTO;
import com.goldeneye.service.CustomerService;
import com.goldeneye.service.LocationService;
import com.goldeneye.service.StoneService;

/**
 *
 * @author dshelby
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class GoldeneyeController {

    private final CustomerService customerService;
    private final LocationService locationService;
    private final StoneService stoneService;

    public GoldeneyeController(CustomerService customerService, LocationService locationService, StoneService stoneService) {
        this.customerService = customerService;
        this.locationService = locationService;
        this.stoneService = stoneService;
    }

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/locations/{custId}")
    public ResponseEntity<List<LocationDTO>> getLocationsByCustId(@PathVariable int custId) {
        return ResponseEntity.ok(locationService.getLocationsByCustId(custId));
    }

    @GetMapping("/stones")
    public ResponseEntity<List<StoneDTO>> getAllStones() {
        return ResponseEntity.ok(stoneService.getAllStones());
    }

}

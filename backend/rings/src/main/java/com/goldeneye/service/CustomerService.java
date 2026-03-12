/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.goldeneye.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.goldeneye.dto.CustomerDTO;
import com.goldeneye.repo.CustomerRepo;

/**
 *
 * @author scanales
 */
@Service
public class CustomerService {

    private final CustomerRepo customerRepo;

    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerRepo.findAll()
            .stream()
            .map(c -> new CustomerDTO(c.getCustId(), c.getName()))
            .toList();
    }



}

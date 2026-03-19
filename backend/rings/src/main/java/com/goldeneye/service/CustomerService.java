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

import com.goldeneye.dto.CustomerDTO;
import com.goldeneye.repo.CustomerRepo;
import com.goldeneye.constants.BoolEnum;

/**
 *
 * @author dshelby
 */
@Service
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepo customerRepo;

    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public List<CustomerDTO> getAllCustomers() {
        logger.info("Fetching all customers");
        List<CustomerDTO> customers = customerRepo.findAll()
            .stream()
            .map(c -> new CustomerDTO(c.getCustId(), c.getName(), c.getActive()))
            .toList();
        logger.debug("Retrieved {} customers", customers.size());
        return customers;
    }

    @Transactional
    public int createCustomer(CustomerDTO customer) {
        logger.info("Creating new customer with name: {}", customer.getName());
        customerRepo.insertCustomer(customer.getName());
        int newId = customerRepo.getLastGeneratedId();
        logger.info("Customer created successfully with ID: {}", newId);
        return newId;
    }
  
    public void updateCustomer(int custId, CustomerDTO customer) {
        logger.info("Updating customer with ID: {}", custId);
        customerRepo.updateCustomer(custId, customer.getName());
        logger.debug("Customer ID {} updated with name: {}", custId, customer.getName());
    }

    public void deleteCustomer(int custId) {
        logger.info("Deleting (deactivating) customer with ID: {}", custId);
        int activeFalse = BoolEnum.FALSE.ordinal();
        customerRepo.deleteCustomer(custId, activeFalse);
        logger.debug("Customer ID {} marked as inactive", custId);
    }
}

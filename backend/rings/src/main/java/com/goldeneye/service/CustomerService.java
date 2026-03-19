/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.goldeneye.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goldeneye.dto.CustomerDTO;
import com.goldeneye.repo.CustomerRepo;
import com.goldeneye.constants.BoolEnum;

/**
 *
 * @author dshelby
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
            .map(c -> new CustomerDTO(c.getCustId(), c.getName(), c.getActive()))
            .toList();
    }

    @Transactional
    public int createCustomer(CustomerDTO customer) {
        customerRepo.insertCustomer(customer.getName());
        return customerRepo.getLastGeneratedId();
    }
  
    public void updateCustomer(int custId, CustomerDTO customer) {
        customerRepo.updateCustomer(custId, customer.getName());
    }

    public void deleteCustomer(int custId) {
        int activeFalse = BoolEnum.FALSE.ordinal();
        customerRepo.deleteCustomer(custId, activeFalse);
    }
}

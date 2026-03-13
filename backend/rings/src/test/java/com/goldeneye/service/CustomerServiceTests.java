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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.goldeneye.dto.CustomerDTO;
import com.goldeneye.rings.RingsApplication;
 
/**
 *
 * @author scanales
 */

@SpringBootTest(classes = RingsApplication.class)
@DisplayName("Customer Service Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class CustomerServiceTests {

    @Autowired
    private CustomerService customerService;

    @Test
    void getAllCustomersReturnsListOfCustomerDTOs() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        assertNotNull(customers);
        assertFalse(customers.isEmpty());
        assertTrue(customers.stream().allMatch(c -> c instanceof CustomerDTO));
        assertEquals(20, customers.size());
        assertTrue(customers.stream().anyMatch(c -> c.getCustId() == 1 && c.getName().equals("Golden Halo Jewelers")));
        assertTrue(customers.stream().anyMatch(c -> c.getCustId() == 20 && c.getName().equals("Precious Band Co.")));
    }
}
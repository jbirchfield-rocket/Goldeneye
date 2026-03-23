/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.goldeneye.service;

import java.util.Collections;
import java.util.List;

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

import com.goldeneye.dto.CustomerDTO;
import com.goldeneye.exception.ResourceNotFoundException;
import com.goldeneye.model.Customer;
import com.goldeneye.repo.CustomerRepo;
 
/**
 *
 * @author scanales kwall
 */

@DisplayName("Customer Service Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
public class CustomerServiceTests {

    @Mock
    private CustomerRepo customerRepo;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer1;
    private Customer customer2;

    @BeforeEach
    void setUpDTOs() {
        customer1 = new Customer(1, "Golden Halo Jewelers", 1);
        customer2 = new Customer(20, "Precious Band Co.", 1);
    }

    @Test
    void getAllCustomersReturnsListOfCustomerDTOs() {
        when(customerRepo.findAll()).thenReturn(List.of(customer1, customer2));

        List<CustomerDTO> customers = customerService.getAllCustomers();

        assertNotNull(customers);
        assertFalse(customers.isEmpty());
        assertEquals(2, customers.size());
        assertTrue(customers.stream().allMatch(c -> c instanceof CustomerDTO));
        assertTrue(customers.stream().anyMatch(c -> c.getCustId() == 1 && c.getName().equals("Golden Halo Jewelers")));
        assertTrue(customers.stream().anyMatch(c -> c.getCustId() == 20 && c.getName().equals("Precious Band Co.")));
    }

    @Test
    void createCustomerReturnsNewCustomerId() {
        CustomerDTO newCustomer = new CustomerDTO(0, "Golden Halo Jewelers", 1);

        when(customerRepo.getLastGeneratedId()).thenReturn(1);

        int custId = customerService.createCustomer(newCustomer);

        assertEquals(1, custId);
        verify(customerRepo).insertCustomer("Golden Halo Jewelers");
    }

    @Test
    void updateCustomerUpdatesCorrectCustomer() {
        CustomerDTO updatedCustomer = new CustomerDTO(1, "Golden Halo Jewelers Updated", 1);

        when(customerRepo.existsById(1)).thenReturn(true);

        customerService.updateCustomer(1, updatedCustomer);

        verify(customerRepo).updateCustomer(1, "Golden Halo Jewelers Updated");
    }

    @Test
    void getAllCustomersThrowsWhenNoCustomersFound() {
        when(customerRepo.findAll()).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> customerService.getAllCustomers());
    }

    @Test
    void createCustomerThrowsWhenNameIsNull() {
        CustomerDTO nullNameCustomer = new CustomerDTO(0, null, 1);

        assertThrows(IllegalArgumentException.class, () -> customerService.createCustomer(nullNameCustomer));
    }

    @Test
    void createCustomerThrowsWhenNameIsBlank() {
        CustomerDTO blankNameCustomer = new CustomerDTO(0, "   ", 1);

        assertThrows(IllegalArgumentException.class, () -> customerService.createCustomer(blankNameCustomer));
    }

    @Test
    void updateCustomerThrowsWhenCustomerNotFound() {
        CustomerDTO customer = new CustomerDTO(99, "Test", 1);

        when(customerRepo.existsById(99)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> customerService.updateCustomer(99, customer));
    }

    @Test
    void updateCustomerThrowsWhenNameIsNull() {
        CustomerDTO nullNameCustomer = new CustomerDTO(1, null, 1);

        when(customerRepo.existsById(1)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> customerService.updateCustomer(1, nullNameCustomer));
    }

    @Test
    void updateCustomerThrowsWhenNameIsBlank() {
        CustomerDTO blankNameCustomer = new CustomerDTO(1, "   ", 1);

        when(customerRepo.existsById(1)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> customerService.updateCustomer(1, blankNameCustomer));
    }

    @Test
    void deleteCustomerDeletesCorrectCustomer() {
        when(customerRepo.existsById(1)).thenReturn(true);

        customerService.deleteCustomer(1);

        verify(customerRepo).deleteCustomer(1, 0);
    }

    @Test
    void deleteCustomerThrowsWhenCustomerNotFound() {
        when(customerRepo.existsById(99)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> customerService.deleteCustomer(99));
    }
    
}
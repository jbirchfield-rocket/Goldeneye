/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

package com.goldeneye.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

/**
 *
 * @author kwall
 */

@DisplayName("Customer Entity Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class CustomerTest {

    @Test
    public void constructor_initializesAllFields() {
        Customer customer = new Customer(101, "Alice", 1);

        assertEquals(101, customer.getCustId());
        assertEquals("Alice", customer.getName());
        assertEquals(1, customer.getActive());
    }

    @Test
    public void setCustId_updatesValue() {
        Customer customer = new Customer(101, "Alice", 1);

        customer.setCustId(202);

        assertEquals(202, customer.getCustId());
    }

    @Test
    public void setName_updatesValue() {
        Customer customer = new Customer(101, "Alice", 1);

        customer.setName("Bob");

        assertEquals("Bob", customer.getName());
    }

    @Test
    public void setName_allowsNull() {
        Customer customer = new Customer(101, "Alice", 1);

        customer.setName(null);

        assertNull(customer.getName());
    }
}
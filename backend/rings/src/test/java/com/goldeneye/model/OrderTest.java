/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.goldeneye.model;

import java.time.LocalDate;

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

@DisplayName("Order Entity Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class OrderTest {

    @Test
    public void constructor_initializesAllFields() {
        LocalDate orderDate = LocalDate.of(2026, 3, 11);

        Order order = new Order(1, 101, 5, 1, orderDate);

        assertEquals(1, order.getOrderId());
        assertEquals(101, order.getCustId());
        assertEquals(5, order.getLocationId());
        assertEquals(1, order.getBillLocId());
        assertEquals(orderDate, order.getOrderDate());
    }

    @Test
    public void setOrderId_updatesValue() {
        Order order = new Order(1, 101, 5, 1, LocalDate.of(2026, 3, 11));

        order.setOrderId(2);

        assertEquals(2, order.getOrderId());
    }

    @Test
    public void setCustId_updatesValue() {
        Order order = new Order(1, 101, 5, 1, LocalDate.of(2026, 3, 11));

        order.setCustId(202);

        assertEquals(202, order.getCustId());
    }

    @Test
    public void setBillLocId_updatesValue() {
        Order order = new Order(1, 101, 5, 1, LocalDate.of(2026, 3, 11));
        order.setBillLocId(2);

        assertEquals(2, order.getBillLocId());
    }

    @Test
    public void setLocationId_updatesValue() {
        Order order = new Order(1, 101, 5, 1, LocalDate.of(2026, 3, 11));

        order.setLocationId(10);

        assertEquals(10, order.getLocationId());
    }

    @Test
    public void setOrderDate_updatesValue() {
        Order order = new Order(1, 101, 5, 1, LocalDate.of(2026, 3, 11));
        LocalDate updatedDate = LocalDate.of(2026, 4, 1);

        order.setOrderDate(updatedDate);

        assertEquals(updatedDate, order.getOrderDate());
    }

    @Test
    public void setOrderDate_allowsNull() {
        Order order = new Order(1, 101, 5, 1, LocalDate.of(2026, 3, 11));

        order.setOrderDate(null);

        assertNull(order.getOrderDate());
    }
}
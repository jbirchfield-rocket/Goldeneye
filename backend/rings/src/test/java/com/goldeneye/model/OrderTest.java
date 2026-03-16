/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.goldeneye.model;

import java.math.BigDecimal;
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
        BigDecimal totalPrice = new BigDecimal("499.99");

        Order order = new Order(1, 101, orderDate, totalPrice);

        assertEquals(1, order.getOrderId());
        assertEquals(101, order.getCustId());
        assertEquals(orderDate, order.getOrderDate());
        assertEquals(0, totalPrice.compareTo(order.getTotalPrice()));
    }

    @Test
    public void setOrderId_updatesValue() {
        Order order = new Order(1, 101, LocalDate.of(2026, 3, 11), new BigDecimal("499.99"));

        order.setOrderId(2);

        assertEquals(2, order.getOrderId());
    }

    @Test
    public void setCustId_updatesValue() {
        Order order = new Order(1, 101, LocalDate.of(2026, 3, 11), new BigDecimal("499.99"));

        order.setCustId(202);

        assertEquals(202, order.getCustId());
    }

    @Test
    public void setOrderDate_updatesValue() {
        Order order = new Order(1, 101, LocalDate.of(2026, 3, 11), new BigDecimal("499.99"));
        LocalDate updatedDate = LocalDate.of(2026, 4, 1);

        order.setOrderDate(updatedDate);

        assertEquals(updatedDate, order.getOrderDate());
    }

    @Test
    public void setTotalPrice_updatesValue() {
        Order order = new Order(1, 101, LocalDate.of(2026, 3, 11), new BigDecimal("499.99"));
        BigDecimal updatedPrice = new BigDecimal("599.50");

        order.setTotalPrice(updatedPrice);

        assertEquals(0, updatedPrice.compareTo(order.getTotalPrice()));
    }

    @Test
    public void setters_allowNullForReferenceFields() {
        Order order = new Order(1, 101, LocalDate.of(2026, 3, 11), new BigDecimal("499.99"));

        order.setOrderDate(null);
        order.setTotalPrice(null);

        assertNull(order.getOrderDate());
        assertNull(order.getTotalPrice());
    }
}
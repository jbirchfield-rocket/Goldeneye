/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.goldeneye.model;

import java.math.BigDecimal;

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

@DisplayName("OrderItem Entity Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class OrderItemTest {

    @Test
    public void constructor_initializesAllFields() {
        BigDecimal unitPrice = new BigDecimal("89.99");

        OrderItem orderItem = new OrderItem(1, 10, 20, 30, 40, 50, unitPrice, 2);

        assertEquals(1, orderItem.getOrderItemId());
        assertEquals(10, orderItem.getOrderId());
        assertEquals(20, orderItem.getProductId());
        assertEquals(30, orderItem.getMaterialId());
        assertEquals(40, orderItem.getWidthId());
        assertEquals(50, orderItem.getStoneId());
        assertEquals(0, unitPrice.compareTo(orderItem.getUnitPrice()));
        assertEquals(2, orderItem.getQuantity());
    }

    @Test
    public void setOrderId_updatesValue() {
        OrderItem orderItem = new OrderItem(1, 10, 20, 30, 40, 50, new BigDecimal("89.99"), 2);

        orderItem.setOrderId(5);

        assertEquals(5, orderItem.getOrderId());
    }

    @Test
    public void setProductId_updatesValue() {
        OrderItem orderItem = new OrderItem(1, 10, 20, 30, 40, 50, new BigDecimal("89.99"), 2);

        orderItem.setProductId(11);

        assertEquals(11, orderItem.getProductId());
    }

    @Test
    public void setMaterialId_updatesValue() {
        OrderItem orderItem = new OrderItem(1, 10, 20, 30, 40, 50, new BigDecimal("89.99"), 2);

        orderItem.setMaterialId(21);

        assertEquals(21, orderItem.getMaterialId());
    }

    @Test
    public void setWidthId_updatesValue() {
        OrderItem orderItem = new OrderItem(1, 10, 20, 30, 40, 50, new BigDecimal("89.99"), 2);

        orderItem.setWidthId(31);

        assertEquals(31, orderItem.getWidthId());
    }

    @Test
    public void setStoneId_updatesValue() {
        OrderItem orderItem = new OrderItem(1, 10, 20, 30, 40, 50, new BigDecimal("89.99"), 2);

        orderItem.setStoneId(41);

        assertEquals(41, orderItem.getStoneId());
    }

    @Test
    public void setUnitPrice_updatesValue() {
        OrderItem orderItem = new OrderItem(1, 10, 20, 30, 40, 50, new BigDecimal("89.99"), 2);
        BigDecimal updatedPrice = new BigDecimal("99.50");

        orderItem.setUnitPrice(updatedPrice);

        assertEquals(0, updatedPrice.compareTo(orderItem.getUnitPrice()));
    }

    @Test
    public void setQuantity_updatesValue() {
        OrderItem orderItem = new OrderItem(1, 10, 20, 30, 40, 50, new BigDecimal("89.99"), 2);

        orderItem.setQuantity(7);

        assertEquals(7, orderItem.getQuantity());
    }

    @Test
    public void setUnitPrice_allowsNull() {
        OrderItem orderItem = new OrderItem(1, 10, 20, 30, 40, 50, new BigDecimal("89.99"), 2);

        orderItem.setUnitPrice(null);

        assertNull(orderItem.getUnitPrice());
    }
}
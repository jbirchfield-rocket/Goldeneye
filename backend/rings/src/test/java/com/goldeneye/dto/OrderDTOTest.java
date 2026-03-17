/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.goldeneye.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
 
/**
 *
 * @author scanales
 */

@DisplayName("Order DTO Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class OrderDTOTest {

    @Test
    void constructorInitializesAllFields() {
        // OrderItem DTO: productId, materialId, widthId, stoneId, unitPrice, quantity
        // Order DTO: orderId, custId, locationId, date, List of orderItems
        OrderItemDTO orderItemDto1 = new OrderItemDTO(1, 2, 3,4, BigDecimal.valueOf(9.99), 6);
        OrderItemDTO orderItemDto2 = new OrderItemDTO(2, 3, 4, 5, BigDecimal.valueOf(10.99), 7);
        OrderDTO orderDto = new OrderDTO(
            1, 
            1, 
            1,
            LocalDate.of(2026, 3, 17),
            List.of(orderItemDto1, orderItemDto2)
        );

        assertNotNull(orderDto);
        assertEquals(1, orderDto.getOrderId());
        assertEquals(1, orderDto.getCustId());
        assertEquals(1, orderDto.getLocationId());
        assertEquals(LocalDate.of(2026, 3, 17), orderDto.getDate());
        assertNotNull(orderDto.getOrderItems());
        assertEquals(2, orderDto.getOrderItems().size());
        assertEquals(orderItemDto1, orderDto.getOrderItems().get(0));
        assertEquals(orderItemDto2, orderDto.getOrderItems().get(1));
    } 
}
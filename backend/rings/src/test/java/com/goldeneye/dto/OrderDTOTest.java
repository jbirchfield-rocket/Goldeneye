/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.goldeneye.dto;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
        OrderItemDTO orderItemDto1 = new OrderItemDTO(1, 2, 3, 4, 5, 6);
        OrderItemDTO orderItemDto2 = new OrderItemDTO(2, 3, 4, 5, 6, 7);
        OrderDTO orderDto = new OrderDTO (
            1, 
            1, 
            1,
            1,
            LocalDate.of(2026, 3, 17),
            List.of(orderItemDto1, orderItemDto2)
        );

        assertNotNull(orderDto);
        assertEquals(1, orderDto.getOrderId());
        assertEquals(Integer.valueOf(1), orderDto.getCustId());
        assertEquals(1, orderDto.getLocationId());
        assertEquals(1, orderDto.getBillLocId());
        assertEquals(LocalDate.of(2026, 3, 17), orderDto.getDate());
        assertNotNull(orderDto.getOrderItems());
        assertEquals(2, orderDto.getOrderItems().size());
        assertEquals(orderItemDto1, orderDto.getOrderItems().get(0));
        assertEquals(orderItemDto2, orderDto.getOrderItems().get(1));
    }

    @Test
    void noArgConstructorCreatesInstance() {
        OrderDTO orderDto = new OrderDTO();
        assertNotNull(orderDto);
        assertNull(orderDto.getOrderId());
        assertEquals(0, orderDto.getCustId());
        assertEquals(0, orderDto.getLocationId());
        assertEquals(0, orderDto.getBillLocId());
        assertNull(orderDto.getDate());
        assertNull(orderDto.getOrderItems());
    }

    @Test
    void settersUpdateFields() {
        OrderItemDTO item1 = new OrderItemDTO(1, 2, 3, 4, 5, 6);
        OrderItemDTO item2 = new OrderItemDTO(2, 3, 4, 5, 6, 7);
        LocalDate date = LocalDate.of(2026, 1, 1);

        OrderDTO orderDto = new OrderDTO();
        orderDto.setOrderId(10);
        orderDto.setCustId(20);
        orderDto.setLocationId(30);
        orderDto.setBillLocId(40);
        orderDto.setDate(date);
        orderDto.setOrderItems(List.of(item1, item2));

        assertEquals(10, orderDto.getOrderId());
        assertEquals(20, orderDto.getCustId());
        assertEquals(30, orderDto.getLocationId());
        assertEquals(40, orderDto.getBillLocId());
        assertEquals(date, orderDto.getDate());
        assertNotNull(orderDto.getOrderItems());
        assertEquals(2, orderDto.getOrderItems().size());
        assertEquals(item1, orderDto.getOrderItems().get(0));
        assertEquals(item2, orderDto.getOrderItems().get(1));
    }
}
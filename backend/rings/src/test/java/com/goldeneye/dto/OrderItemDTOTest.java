/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.goldeneye.dto;

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

@DisplayName("OrderItem DTO Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class OrderItemDTOTest {

    @Test
    void constructorInitializesAllFields() {
        // OrderItem DTO: orderItemId, productId, materialId, widthId, stoneId, quantity
        OrderItemDTO orderItemDto = new OrderItemDTO(1, 2, 3, 4, 5, 6);
        assertNotNull(orderItemDto);
        assertEquals(1, orderItemDto.getOrderItemId());
        assertEquals(2, orderItemDto.getProductId());
        assertEquals(3, orderItemDto.getMaterialId());
        assertEquals(4, orderItemDto.getWidthId());
        assertEquals(5, orderItemDto.getStoneId());
        assertEquals(6, orderItemDto.getQuantity());
    }

    @Test
    void noArgConstructorCreatesInstance() {
        OrderItemDTO orderItemDto = new OrderItemDTO();
        assertNotNull(orderItemDto);
        assertEquals(0, orderItemDto.getProductId());
        assertEquals(0, orderItemDto.getMaterialId());
        assertEquals(0, orderItemDto.getWidthId());
        assertEquals(0, orderItemDto.getStoneId());
        assertEquals(0, orderItemDto.getQuantity());
    }

    @Test
    void settersUpdateFields() {
        OrderItemDTO orderItemDto = new OrderItemDTO();
        orderItemDto.setOrderItemId(7);
        orderItemDto.setProductId(8);
        orderItemDto.setMaterialId(9);
        orderItemDto.setWidthId(10);
        orderItemDto.setStoneId(11);
        orderItemDto.setQuantity(12);

        assertEquals(7, orderItemDto.getOrderItemId());
        assertEquals(8, orderItemDto.getProductId());
        assertEquals(9, orderItemDto.getMaterialId());
        assertEquals(10, orderItemDto.getWidthId());
        assertEquals(11, orderItemDto.getStoneId());
        assertEquals(12, orderItemDto.getQuantity());
    }
}
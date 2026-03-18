/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.goldeneye.dto;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static com.goldeneye.constants.AppConstants.PRICESCALE;
 
/**
 *
 * @author scanales
 */

@DisplayName("OrderItem DTO Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class OrderItemDTOTest {

    @Test
    void constructorInitializesAllFields() {
        // OrderItem DTO: productId, materialId, widthId, stoneId, unitPrice, quantity
        OrderItemDTO orderItemDto = new OrderItemDTO(1, 2, 3,4, BigDecimal.valueOf(9.99).setScale(PRICESCALE), 6);
        assertNotNull(orderItemDto);
        assertEquals(1, orderItemDto.getProductId());
        assertEquals(2, orderItemDto.getMaterialId());
        assertEquals(3, orderItemDto.getWidthId());
        assertEquals(4, orderItemDto.getStoneId());
        assertEquals(0, orderItemDto.getUnitPrice().compareTo(BigDecimal.valueOf(9.99)));
        assertEquals(6, orderItemDto.getQuantity());
    } 
}
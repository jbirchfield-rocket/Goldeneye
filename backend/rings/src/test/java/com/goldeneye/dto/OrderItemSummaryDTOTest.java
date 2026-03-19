/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.goldeneye.dto;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

/**
 *
 * @author kwall
 */
@DisplayName("OrderItemSummary DTO Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class OrderItemSummaryDTOTest {

    @Test
    void noArgConstructorCreatesInstance() {
        OrderItemSummaryDTO dto = new OrderItemSummaryDTO();
        assertNotNull(dto);
        assertNull(dto.getOrderItemId());
        assertNull(dto.getProductName());
        assertNull(dto.getMaterialName());
        assertEquals(0, dto.getWidth());
        assertNull(dto.getStoneName());
        assertNull(dto.getUnitPrice());
        assertEquals(0, dto.getQuantity());
    }

    @Test
    void constructorInitializesAllFields() {
        BigDecimal price = new BigDecimal("29.99");
        OrderItemSummaryDTO dto = new OrderItemSummaryDTO(1, "Band", "Gold", 6, "Diamond", price, 3);

        assertNotNull(dto);
        assertEquals(1, dto.getOrderItemId());
        assertEquals("Band", dto.getProductName());
        assertEquals("Gold", dto.getMaterialName());
        assertEquals(6, dto.getWidth());
        assertEquals("Diamond", dto.getStoneName());
        assertEquals(price, dto.getUnitPrice());
        assertEquals(3, dto.getQuantity());
    }

    @Test
    void settersUpdateFields() {
        BigDecimal price = new BigDecimal("49.99");
        OrderItemSummaryDTO dto = new OrderItemSummaryDTO();

        dto.setOrderItemId(10);
        dto.setProductName("Ring");
        dto.setMaterialName("Silver");
        dto.setWidth(8);
        dto.setStoneName("Emerald");
        dto.setUnitPrice(price);
        dto.setQuantity(2);

        assertEquals(10, dto.getOrderItemId());
        assertEquals("Ring", dto.getProductName());
        assertEquals("Silver", dto.getMaterialName());
        assertEquals(8, dto.getWidth());
        assertEquals("Emerald", dto.getStoneName());
        assertEquals(price, dto.getUnitPrice());
        assertEquals(2, dto.getQuantity());
    }
}
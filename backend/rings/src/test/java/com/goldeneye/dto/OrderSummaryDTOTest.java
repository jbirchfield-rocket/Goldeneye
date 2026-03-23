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
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

/**
 *
 * @author kwall
 */
@DisplayName("OrderSummary DTO Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class OrderSummaryDTOTest {

    @Test
    void noArgConstructorCreatesInstance() {
        OrderSummaryDTO dto = new OrderSummaryDTO();
        assertNotNull(dto);
        assertEquals(0, dto.getOrderId());
        assertNull(dto.getCustomerName());
        assertNull(dto.getOrderDate());
        assertNull(dto.getLocation());
        assertNull(dto.getBillingLocation());
        assertNull(dto.getOrderItems());
    }

    @Test
    void constructorInitializesAllFields() {
        LocationDTO location = new LocationDTO(1, 1, "123 Main St", "Springfield", "IL", "62701");
        LocationDTO billingLocation = new LocationDTO(2, 1, "456 Oak Ave", "Springfield", "IL", "62702");
        OrderItemSummaryDTO item1 = new OrderItemSummaryDTO(1, "Band", "Gold", 6, "Diamond", new BigDecimal("29.99"), 2);
        OrderItemSummaryDTO item2 = new OrderItemSummaryDTO(2, "Ring", "Silver", 8, "Emerald", new BigDecimal("49.99"), 1);
        LocalDate date = LocalDate.of(2026, 3, 19);

        OrderSummaryDTO dto = new OrderSummaryDTO(1, "John Doe", date, location, billingLocation, List.of(item1, item2));

        assertNotNull(dto);
        assertEquals(1, dto.getOrderId());
        assertEquals("John Doe", dto.getCustomerName());
        assertEquals(date, dto.getOrderDate());
        assertEquals(location, dto.getLocation());
        assertEquals(billingLocation, dto.getBillingLocation());
        assertNotNull(dto.getOrderItems());
        assertEquals(2, dto.getOrderItems().size());
        assertEquals(item1, dto.getOrderItems().get(0));
        assertEquals(item2, dto.getOrderItems().get(1));
    }

    @Test
    void settersUpdateFields() {
        LocationDTO location = new LocationDTO(3, 2, "789 Pine Rd", "Chicago", "IL", "60601");
        LocationDTO billingLocation = new LocationDTO(4, 2, "101 Elm St", "Chicago", "IL", "60602");
        OrderItemSummaryDTO item = new OrderItemSummaryDTO(3, "Pendant", "Platinum", 4, "Ruby", new BigDecimal("99.99"), 1);
        LocalDate date = LocalDate.of(2026, 1, 15);

        OrderSummaryDTO dto = new OrderSummaryDTO();
        dto.setOrderId(5);
        dto.setCustomerName("Jane Smith");
        dto.setOrderDate(date);
        dto.setLocation(location);
        dto.setBillingLocation(billingLocation);
        dto.setOrderItems(List.of(item));

        assertEquals(5, dto.getOrderId());
        assertEquals("Jane Smith", dto.getCustomerName());
        assertEquals(date, dto.getOrderDate());
        assertEquals(location, dto.getLocation());
        assertEquals(billingLocation, dto.getBillingLocation());
        assertNotNull(dto.getOrderItems());
        assertEquals(1, dto.getOrderItems().size());
        assertEquals(item, dto.getOrderItems().get(0));
    }
}
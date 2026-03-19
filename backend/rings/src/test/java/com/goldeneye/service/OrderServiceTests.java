/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.goldeneye.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.goldeneye.dto.OrderDTO;
import com.goldeneye.dto.ProductDTO;
import com.goldeneye.dto.StoneDTO;
import com.goldeneye.dto.MaterialDTO;
import com.goldeneye.dto.WidthDTO;
import com.goldeneye.dto.LocationDTO;
import com.goldeneye.dto.OrderItemDTO;
import com.goldeneye.repo.OrderItemRepo;
import com.goldeneye.repo.OrderRepo;

/**
 *
 * @author kwall, scanales
 */

@ExtendWith(MockitoExtension.class)
public class OrderServiceTests {
    @Mock private OrderRepo orderRepo;
    @Mock private OrderItemRepo orderItemRepo;
    @Mock private ProductService productService;
    @Mock private StoneService stoneService;
    @Mock private MaterialService materialService;
    @Mock private WidthService widthService;
    @Mock private LocationService locationService;

    @InjectMocks
    private OrderService orderService;

    private OrderItemDTO item;
    private ProductDTO product;
    private StoneDTO stone;
    private MaterialDTO material;
    private WidthDTO width;
    private LocationDTO location;

    @BeforeEach
    void initializeDTOs() {
        item = new OrderItemDTO(1, 1, 1, 1, 1, 3);
        product = new ProductDTO(1, "Test Product", "Test Description", BigDecimal.valueOf(100));
        stone = new StoneDTO(1, "Test Stone", 1, BigDecimal.valueOf(50));
        material = new MaterialDTO(1, "Test Material", 100, 1.5f);
        width = new WidthDTO(1, 2, 2.0f, 50);
        location = new LocationDTO(1, 2, "Test Street", "Test City", "TS", "12345");
    }

    @Test
    void calculateUnitPriceCalculatesCorrectly() {
        when(productService.getProductById(1)).thenReturn(product);
        when(stoneService.getStoneById(1)).thenReturn(stone);
        when(materialService.getMaterialById(1)).thenReturn(material);
        when(widthService.getWidthById(1)).thenReturn(width);

        BigDecimal unitPriceForTestOrder = orderService.calculateUnitPrice(1, 1, 1, 1, 3);

        // Product: $100, Stone: $50, Mat: 1.5, width: 2.0, qty:3
        // ((100*1.5*2.0) + 50) * 3 = 1050.00
        assertEquals(0, unitPriceForTestOrder.compareTo(BigDecimal.valueOf(1050.00)));
    }

    @Test
    void createOrderCreatesValidOrder() {
        OrderDTO testOrderDTO = new OrderDTO(1, 2, 1, 1, LocalDate.of(2026, 3, 11), List.of(item));

        when(orderRepo.getLastGeneratedId()).thenReturn(40);
        when(productService.getProductById(1)).thenReturn(product);
        when(stoneService.getStoneById(1)).thenReturn(stone);
        when(materialService.getMaterialById(1)).thenReturn(material);
        when(widthService.getWidthById(1)).thenReturn(width);

        int orderId = orderService.createOrder(testOrderDTO);

        assertEquals(40, orderId);
        verify(orderRepo).insertOrder(2, 1, 1);
        verify(orderItemRepo).insertOrderItem(eq(40), eq(1), eq(1), eq(1), eq(1), any(BigDecimal.class), eq(3));
    }

    @Test
    void createOrderThrowsWhenNoItems() {
        OrderDTO emptyOrderDTO = new OrderDTO(1, 2, 1, 1, LocalDate.of(2026, 3, 11), List.of());

        try {
            orderService.createOrder(emptyOrderDTO);
        } catch (Exception e) {
            assertEquals("Order must contain at least one item.", e.getMessage());
        }
    }

    @Test
    void createOrderThrowsWhenNullItems() {
        OrderDTO nullItemsOrderDTO = new OrderDTO(1, 2, 1, 1, LocalDate.of(2026, 3, 11), null);

        try {
            orderService.createOrder(nullItemsOrderDTO);
        } catch (Exception e) {
            assertEquals("Order must contain at least one item.", e.getMessage());
        }
    }
}
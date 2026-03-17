/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.goldeneye.service;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.goldeneye.dto.MaterialDTO;
import com.goldeneye.dto.OrderDTO;
import com.goldeneye.dto.OrderItemDTO;
import com.goldeneye.dto.ProductDTO;
import com.goldeneye.dto.StoneDTO;
import com.goldeneye.dto.WidthDTO;
import com.goldeneye.repo.OrderItemRepo;
import com.goldeneye.repo.OrderRepo;
import com.goldeneye.rings.RingsApplication;

/**
 *
 * @author kwall
 */
@SpringBootTest(classes = RingsApplication.class)
@DisplayName("Order Service Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Tag("integration")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderServiceTests {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderItemRepo orderItemRepo;

    private OrderDTO testOrderDTO;
    private int createdOrderId = 0;

    @BeforeAll
    void initializeTestOrderDTO() {
        OrderItemDTO item = new OrderItemDTO();
        item.setProductId(1); //values ('Standard Fit Grooved Band','A classic band with a center groove for a clean look.', 120)
        item.setMaterialId(1); //values ('Gold Plated', 71, 0.500);
        item.setWidthId(1); //values (2, 0.9, 1);
        item.setStoneId(1); //values ('None', 9, 0);
        item.setQuantity(2);

        testOrderDTO = new OrderDTO();
        testOrderDTO.setCustId(2);
        testOrderDTO.setLocationId(1);
        testOrderDTO.setOrderItems(List.of(item));
    }

    @Test
    void orderServiceIsNotNull() {
        assertNotNull(orderService);
    }

    @Test
    void calculateUnitPriceCalculatesCorrectly() {
        BigDecimal unitPriceForTestOrder = orderService.calculateUnitPrice(1, 1, 1, 1, 1);

        ProductDTO product = productService.getProductById(productId);
        BigDecimal basePrice = product.getBasePrice();
        // get stone price from stone
        StoneDTO stone = stoneService.getStoneById(stoneId);
        BigDecimal stonePrice = stone.getPrice();
        // get material multiplier from material
        MaterialDTO material = materialService.getMaterialById(materialId);
        float materialMultiplier = material.getMultiplier();
        // get width multiplier from width
        WidthDTO width = widthService.getWidthById(widthId);
        float widthMultiplier = width.getMultiplier();
        // calculate final price: (stone price + (base price * material multiplier * width multiplier)) * quantity
        BigDecimal ringPrice = basePrice
            .multiply(BigDecimal.valueOf(materialMultiplier))
            .multiply(BigDecimal.valueOf(widthMultiplier));
        
        BigDecimal individualPrice = stonePrice.add(ringPrice);
        BigDecimal totalPrice = individualPrice.multiply(BigDecimal.valueOf(quantity));

        assertNotNull(unitPriceForTestOrder);
        assertTrue(unitPriceForTestOrder.compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    @Transactional
    void createOrderReturnsValidOrderId() {
        createdOrderId = orderService.createOrder(testOrderDTO);
        assertTrue(createdOrderId > 0);
    }

    @AfterAll
    void tearDownCreatedOrder() {
        if (createdOrderId > 0) {
            orderRepo.deleteById(createdOrderId);
        }
    }
}
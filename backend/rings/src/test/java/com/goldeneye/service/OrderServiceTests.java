/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.goldeneye.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.goldeneye.dto.OrderDTO;
import com.goldeneye.dto.OrderItemDTO;
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

    private OrderDTO testOrderDTO;

    @BeforeAll
    void initializeTestOrderDTO() {
        OrderItemDTO item = new OrderItemDTO(1, 1, 1, 1, 1, 3);
    

        testOrderDTO = new OrderDTO(1, 2, 1, 1, LocalDate.of(2026, 3, 11), List.of(item));
    }

    @Test
    void orderServiceIsNotNull() {
        assertNotNull(orderService);
    }

    @Test
    void calculateUnitPriceCalculatesCorrectly() {
        BigDecimal unitPriceForTestOrder = orderService.calculateUnitPrice(1, 2, 1, 1, 3);
        assertEquals(0, unitPriceForTestOrder.compareTo(BigDecimal.valueOf(324)));
    }

    // cant test create order
    // @Test 
    // @Transactional
    // void createOrderReturnsValidOrder() {
    //     createdOrderId = orderService.createOrder(testOrderDTO);
        
    // }

    // cant use delete 
    // @AfterAll
    // void tearDownCreatedOrder() {
    //     if (createdOrderId > 0) {
    //         orderRepo.deleteById(createdOrderId);
    //     }
    // }
}
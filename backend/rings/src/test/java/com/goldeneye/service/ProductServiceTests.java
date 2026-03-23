/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.goldeneye.service;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.goldeneye.dto.ProductDTO;
import com.goldeneye.exception.ResourceNotFoundException;
import com.goldeneye.model.Product;
import com.goldeneye.repo.ProductRepo;

/**
 *
 * @author scanales kwall
 */

@DisplayName("Product Service Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class ProductServiceTests {

    @Mock
    private ProductRepo productRepo;

    @InjectMocks
    private ProductService productService;

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUpProducts() {
        product1 = new Product(1, "Standard Fit Grooved Band", "A classic band with a center groove for a clean look.", BigDecimal.valueOf(120));
        product2 = new Product(2, "Comfort Fit Plain Band", "A smooth, rounded interior band for all-day comfort.", BigDecimal.valueOf(100));
    }

    @Test
    void getAllProductsReturnsListOfProductDTOs() {
        when(productRepo.findAll()).thenReturn(List.of(product1, product2));

        List<ProductDTO> products = productService.getAllProducts();

        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals(2, products.size());
        assertTrue(products.stream().allMatch(p -> p instanceof ProductDTO));
        assertTrue(products.stream().anyMatch(p -> p.getProdId() == 1 && p.getName().equals("Standard Fit Grooved Band")));
        assertTrue(products.stream().anyMatch(p -> p.getProdId() == 2 && p.getName().equals("Comfort Fit Plain Band")));
    }

    @Test
    void getProductByIdReturnsCorrectProduct() {
        when(productRepo.findById(1)).thenReturn(product1);

        ProductDTO product = productService.getProductById(1);

        assertNotNull(product);
        assertEquals(1, product.getProdId());
        assertEquals("Standard Fit Grooved Band", product.getName());
        assertEquals("A classic band with a center groove for a clean look.", product.getDescription());
        assertEquals(0, product.getBasePrice().compareTo(BigDecimal.valueOf(120)));
    }

    @Test
    void getProductByIdThrowsWhenProductNotFound() {
        when(productRepo.findById(99)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(99));
    }
}
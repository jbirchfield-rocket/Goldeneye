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
 * @author scanales
 */

@DisplayName("Product DTO Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ProductDTOTest {

    @Test
    void constructorInitializesAllFields() {
        // ProductDTO: productID, name, description, basePrice
        ProductDTO productDto = new ProductDTO(1, "Test Product", "This is a test product", BigDecimal.valueOf(9.99));
        assertEquals(1, productDto.getProdId());
        assertEquals("Test Product", productDto.getName());
        assertEquals("This is a test product", productDto.getDescription());
        assertEquals(BigDecimal.valueOf(9.99), productDto.getBasePrice());
    }

    @Test
    void noArgConstructorCreatesInstance() {
        ProductDTO productDto = new ProductDTO();
        assertNotNull(productDto);
        assertEquals(0, productDto.getProdId());
        assertNull(productDto.getName());
        assertNull(productDto.getDescription());
        assertNull(productDto.getBasePrice());
    }
}
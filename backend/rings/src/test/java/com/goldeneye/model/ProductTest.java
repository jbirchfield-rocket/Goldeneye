/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.goldeneye.model;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
/**
 *
 * @author kwall
 */

@DisplayName("Product Entity Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ProductTest {

    @Test
    public void constructor_initializesAllFields() {
        BigDecimal basePrice = new BigDecimal("199.99");

        Product product = new Product(1, "Classic Ring", "14k yellow gold", basePrice);

        assertEquals(1, product.getProdId());
        assertEquals("Classic Ring", product.getName());
        assertEquals("14k yellow gold", product.getDescription());
        assertEquals(0, basePrice.compareTo(product.getBasePrice()));
    }

    @Test
    public void setProdId_updatesValue() {
        Product product = new Product(1, "Classic Ring", "desc", new BigDecimal("100.00"));

        product.setProdId(25);

        assertEquals(25, product.getProdId());
    }

    @Test
    public void setName_updatesValue() {
        Product product = new Product(1, "Classic Ring", "desc", new BigDecimal("100.00"));

        product.setName("Modern Ring");

        assertEquals("Modern Ring", product.getName());
    }

    @Test
    public void setDescription_updatesValue() {
        Product product = new Product(1, "Classic Ring", "desc", new BigDecimal("100.00"));

        product.setDescription("Platinum with diamond");

        assertEquals("Platinum with diamond", product.getDescription());
    }

    @Test
    public void setBasePrice_updatesValue() {
        Product product = new Product(1, "Classic Ring", "desc", new BigDecimal("100.00"));
        BigDecimal updatedPrice = new BigDecimal("249.50");

        product.setBasePrice(updatedPrice);

        assertEquals(0, updatedPrice.compareTo(product.getBasePrice()));
    }

    @Test
    public void setters_allowNullForReferenceFields() {
        Product product = new Product(1, "Classic Ring", "desc", new BigDecimal("100.00"));

        product.setName(null);
        product.setDescription(null);
        product.setBasePrice(null);

        assertNull(product.getName());
        assertNull(product.getDescription());
        assertNull(product.getBasePrice());
    }
}
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.goldeneye.dto.ProductDTO;
import com.goldeneye.rings.RingsApplication;
 
/**
 *
 * @author scanales
 */


@SpringBootTest(classes = RingsApplication.class)
@DisplayName("Product Service Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Tag("integration")
class ProductServiceTests {
    @Autowired
    private ProductService productService;

    @Test
    void getAllProductsReturnsListOfProducts() {
       List<ProductDTO> products = productService.getAllProducts();
       assertNotNull(products);
       assertFalse(products.isEmpty());
       assertTrue(products.stream().allMatch(p -> p instanceof ProductDTO));
       assertEquals(4, products.size());
       assertTrue(products.stream().anyMatch(p -> p.getProdId() == 1 && p.getName().equals("Standard Fit Grooved Band")));
    }

    @Test
    void getProductByIdReturnsCorrectProduct() {
        ProductDTO product = productService.getProductById(1);
        assertNotNull(product);
        assertEquals(1, product.getProdId());
        assertEquals("Standard Fit Grooved Band", product.getName());
        assertEquals("A classic band with a center groove for a clean look.", product.getDescription());
        assertEquals(BigDecimal.valueOf(120), product.getBasePrice());
    }

    // @Test
    // @Disabled
    // void readproductsReturnsEmptyListWhenNoproducts() {
    //     List<Product> products = productService.readproducts();
    //     // TODO: Assert list is not null
    //     // TODO: Assert list is empty
    // }

    // @Test
    // @Disabled
    // void createproductAddsNewproduct() {
    //     Product newproduct = new Product(0, "testProduct", "This is a test product.", BigDecimal.valueOf(9.99));
    //     List<Product> startingproducts = productService.readproducts();
    //     assumeTrue(startingproducts != null);
    //     assumeFalse(startingproducts.contains(newproduct));

    //     productService.createproduct(newproduct);
    //     List<Product> updatedproducts = productService.readproducts();
    //     // TODO: Assert list is not null
    //     // TODO: Assert list is not empty
    //     // TODO: Assert list contains the new product
    // }
    

    // @Test
    // @Disabled
    // void updateproductModifiesExistingproduct() {
    //     List<Product> existingproducts = productService.readproducts();
    //     assumeTrue(existingproducts != null);
    //     assumeFalse(existingproducts.isEmpty());

    //     Product productToUpdate = existingproducts.get(0);
    //     productToUpdate.setName("Updated Name");
    //     productService.updateproduct(productToUpdate);

    //     List<Product> updatedproducts = productService.readproducts();
    //     // TODO: Assert list is not null
    //     // TODO: Assert list is not empty
    //     // TODO: Assert updated product has properly been updated
    //     // TODO: Assert other products remain unchanged
    // }

    // @Test
    // @Disabled
    // void deleteproductRemovesproduct() {
        
    // }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.goldeneye.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;

import com.goldeneye.model.Product;
 
/**
 *
 * @author scanales
 */

@DisplayName("Style Service Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StyleServiceTests {
    private static StyleService styleService;

    @BeforeAll
    static void StyleServiceTestsInit() {
        styleService = new StyleService();
    }

    @Test
    void readStylesReturnsListOfStyles() {
       List<Product> styles = styleService.readStyles();
       // TODO: Assert list is not null
       // TODO: Assert list is not empty
       // TODO: Assert list contains expected Product objects
    }

    @Test
    void readStylesReturnsEmptyListWhenNoStyles() {
        List<Product> styles = styleService.readStyles();
        // TODO: Assert list is not null
        // TODO: Assert list is empty
    }

    @Test
    void createStyleAddsNewStyle() {
        Product newStyle = new Product(0, "testProduct", "This is a test product.", BigDecimal.valueOf(9.99));
        List<Product> startingStyles = styleService.readStyles();
        assumeTrue(startingStyles != null);
        assumeFalse(startingStyles.contains(newStyle));

        styleService.createStyle(newStyle);
        List<Product> updatedStyles = styleService.readStyles();
        // TODO: Assert list is not null
        // TODO: Assert list is not empty
        // TODO: Assert list contains the new style
    }
    

    @Test
    void updateStyleModifiesExistingStyle() {
        List<Product> existingStyles = styleService.readStyles();
        assumeTrue(existingStyles != null);
        assumeFalse(existingStyles.isEmpty());

        Product styleToUpdate = existingStyles.get(0);
        styleToUpdate.setName("Updated Name");
        styleService.updateStyle(styleToUpdate);

        List<Product> updatedStyles = styleService.readStyles();
        // TODO: Assert list is not null
        // TODO: Assert list is not empty
        // TODO: Assert updated style has properly been updated
        // TODO: Assert other styles remain unchanged
    }

    @Test
    void deleteStyleRemovesStyle() {
        
    }
}
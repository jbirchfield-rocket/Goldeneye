/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.goldeneye.model;

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
@DisplayName("Material Entity Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class MaterialTest {

    @Test
    public void constructor_initializesAllFields() {
        Material material = new Material(1, "Gold", 100, 1.20f);

        assertEquals(1, material.getMaterialId());
        assertEquals("Gold", material.getMaterialName());
        assertEquals(1.20f, material.getMultiplier(), 0.0001f);
        assertEquals(100, material.getInventory());
    }

    @Test
    public void setMaterialId_updatesValue() {
        Material material = new Material(1, "Gold", 100, 1.20f);

        material.setMaterialId(2);

        assertEquals(2, material.getMaterialId());
    }

    @Test
    public void setMaterialName_updatesValue() {
        Material material = new Material(1, "Gold", 100, 1.20f);

        material.setMaterialName("Platinum");

        assertEquals("Platinum", material.getMaterialName());
    }

    @Test
    public void setMultiplier_updatesValue() {
        Material material = new Material(1, "Gold", 100, 1.20f);

        material.setMultiplier(1.35f);

        assertEquals(1.35f, material.getMultiplier(), 0.0001f);
    }

    @Test
    public void setInventory_updatesValue() {
        Material material = new Material(1, "Gold", 100, 1.20f);

        material.setInventory(125);

        assertEquals(125, material.getInventory());
    }

    @Test
    public void setMaterialName_allowsNull() {
        Material material = new Material(1, "Gold", 100, 1.20f);

        material.setMaterialName(null);

        assertNull(material.getMaterialName());
    }
}
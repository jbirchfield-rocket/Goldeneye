/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.goldeneye.dto;

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

@DisplayName("Material DTO Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class MaterialDTOTest {

    @Test
    void constructorInitializesAllFields() {
        // MaterialDTO: MaterialID, Name, Inventory, Multiplier
        MaterialDTO materialDto = new MaterialDTO(1, "Gold Plated", 100, 1.20f);
        assertEquals(1, materialDto.getMaterialId());
        assertEquals("Gold Plated", materialDto.getName());
        assertEquals(100, materialDto.getInventory());
        assertEquals(1.20f, materialDto.getMultiplier(), 0.0001f);
    }

    @Test
    void noArgConstructorCreatesInstance() {
        MaterialDTO materialDto = new MaterialDTO();
        assertNotNull(materialDto);
        assertEquals(0, materialDto.getMaterialId());
        assertNull(materialDto.getName());
        assertEquals(0, materialDto.getInventory());
        assertEquals(0.0f, materialDto.getMultiplier(), 0.0001f);
    }
}
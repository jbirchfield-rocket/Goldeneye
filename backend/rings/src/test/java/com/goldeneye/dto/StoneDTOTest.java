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

@DisplayName("Stone DTO Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class StoneDTOTest {

    @Test
    void constructorInitializesAllFields() {
        // StoneDTO: StoneID, Name, Inventory, Price
        StoneDTO stoneDto = new StoneDTO(1, "None", 0, BigDecimal.valueOf(1.00));
        assertEquals(1, stoneDto.getStoneId());
        assertEquals("None", stoneDto.getName());
        assertEquals(0, stoneDto.getInventory());
        assertEquals(BigDecimal.valueOf(1.00), stoneDto.getPrice());
    }

    @Test
    void noArgConstructorCreatesInstance() {
        StoneDTO stoneDto = new StoneDTO();
        assertNotNull(stoneDto);
        assertEquals(0, stoneDto.getStoneId());
        assertNull(stoneDto.getName());
        assertEquals(0, stoneDto.getInventory());
        assertNull(stoneDto.getPrice());
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.goldeneye.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
 
/**
 *
 * @author scanales
 */

@DisplayName("Width DTO Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class WidthDTOTest {

    @Test
    void constructorInitializesAllFields() {
        // WidthDTO: WidthID, Name, Multiplier, MaterialUse
        WidthDTO widthDto = new WidthDTO(1, 2, 1.10f, 50);
        assertEquals(1, widthDto.getWidthId());
        assertEquals(2, widthDto.getWidth());
        assertEquals(50, widthDto.getMaterialUse());
        assertEquals(1.10f, widthDto.getMultiplier(), 0.0001f);
    }
}
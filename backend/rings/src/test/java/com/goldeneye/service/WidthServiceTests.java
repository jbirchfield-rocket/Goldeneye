/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.goldeneye.service;

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

import com.goldeneye.dto.WidthDTO;
import com.goldeneye.rings.RingsApplication;
 
/**
 *
 * @author scanales
 */

@SpringBootTest(classes = RingsApplication.class)
@DisplayName("Width Service Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Tag("integration")
public class WidthServiceTests {

    @Autowired
    private WidthService widthService;

    @Test
    void getWidthsReturnsListOfWidthDTOs() {
        // Width Attributes: Id, Name, Multiplier, MaterialUse
        List<WidthDTO> widths = widthService.getAllWidths();
        assertNotNull(widths);
        assertFalse(widths.isEmpty());
        assertTrue(widths.stream().allMatch(w -> w instanceof WidthDTO));
        assertEquals(4, widths.size());
        assertTrue(widths.stream().anyMatch(w -> w.getWidthId() == 1 && w.getWidth() == 2));
        assertTrue(widths.stream().anyMatch(w -> w.getWidthId() == 4 && w.getWidth() == 8));
    }

    @Test
    void getWidthByIdReturnsCorrectWidthDTO() {
        WidthDTO width = widthService.getWidthById(1);
        assertNotNull(width);
        assertEquals(1, width.getWidthId());
        assertEquals(2, width.getWidth());
        assertEquals(0.9f, width.getMultiplier());
        assertEquals(1, width.getMaterialUse());
    }
}
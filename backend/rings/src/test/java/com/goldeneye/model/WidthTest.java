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

@DisplayName("Width Entity Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class WidthTest {

    @Test
    public void constructor_initializesAllFields() {
        Width width = new Width(1, "2mm", 1.10f, 50);

        assertEquals(1, width.getWidthId());
        assertEquals("2mm", width.getWidthName());
        assertEquals(1.10f, width.getMultiplier(), 0.0001f);
        assertEquals(50, width.getMaterialUse());
    }

    @Test
    public void setWidthId_updatesValue() {
        Width width = new Width(1, "2mm", 1.10f, 50);

        width.setWidthId(2);

        assertEquals(2, width.getWidthId());
    }

    @Test
    public void setWidthName_updatesValue() {
        Width width = new Width(1, "2mm", 1.10f, 50);

        width.setWidthName("4mm");

        assertEquals("4mm", width.getWidthName());
    }

    @Test
    public void setMultiplier_updatesValue() {
        Width width = new Width(1, "2mm", 1.10f, 50);

        width.setMultiplier(1.25f);

        assertEquals(1.25f, width.getMultiplier(), 0.0001f);
    }

    @Test
    public void setMaterialUse_updatesValue() {
        Width width = new Width(1, "2mm", 1.10f, 50);

        width.setMaterialUse(75);

        assertEquals(75, width.getMaterialUse());
    }

    @Test
    public void setWidthName_allowsNull() {
        Width width = new Width(1, "2mm", 1.10f, 50);

        width.setWidthName(null);

        assertNull(width.getWidthName());
    }
}
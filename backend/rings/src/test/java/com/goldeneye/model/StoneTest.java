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

@DisplayName("Stone Entity Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class StoneTest {

    @Test
    public void constructor_initializesAllFields() {
        Stone stone = new Stone(1, "Diamond", 1.50f, 25);

        assertEquals(1, stone.getstoneId());
        assertEquals("Diamond", stone.getstoneName());
        assertEquals(1.50f, stone.getMultiplier(), 0.0001f);
        assertEquals(25, stone.getInventory());
    }

    @Test
    public void setstoneId_updatesValue() {
        Stone stone = new Stone(1, "Diamond", 1.50f, 25);

        stone.setstoneId(2);

        assertEquals(2, stone.getstoneId());
    }

    @Test
    public void setstoneName_updatesValue() {
        Stone stone = new Stone(1, "Diamond", 1.50f, 25);

        stone.setstoneName("Ruby");

        assertEquals("Ruby", stone.getstoneName());
    }

    @Test
    public void setMultiplier_updatesValue() {
        Stone stone = new Stone(1, "Diamond", 1.50f, 25);

        stone.setMultiplier(1.75f);

        assertEquals(1.75f, stone.getMultiplier(), 0.0001f);
    }

    @Test
    public void setInventory_updatesValue() {
        Stone stone = new Stone(1, "Diamond", 1.50f, 25);

        stone.setInventory(40);

        assertEquals(40, stone.getInventory());
    }

    @Test
    public void setstoneName_allowsNull() {
        Stone stone = new Stone(1, "Diamond", 1.50f, 25);

        stone.setstoneName(null);

        assertNull(stone.getstoneName());
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
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

@DisplayName("Stone Entity Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class StoneTest {

    @Test
    public void constructor_initializesAllFields() {
        Stone stone = new Stone(1, "Diamond", 10, BigDecimal.valueOf(100.10));
        assertEquals(1, stone.getStoneId());
        assertEquals("Diamond", stone.getStoneName());
        assertEquals(10, stone.getInventory());
        assertEquals(BigDecimal.valueOf(100.10), stone.getPrice());
    }

    @Test
    public void setstoneId_updatesValue() {
        Stone stone = new Stone(1, "Diamond", 5, BigDecimal.valueOf(25));

        stone.setStoneId(2);

        assertEquals(2, stone.getStoneId());
    }

    @Test
    public void setStoneName_updatesValue() {
        Stone stone = new Stone(1, "Diamond", 1, BigDecimal.valueOf(25));

        stone.setStoneName("Ruby");

        assertEquals("Ruby", stone.getStoneName());
    }

    @Test
    public void setInventory_updatesValue() {
        Stone stone = new Stone(1, "Diamond", 1, BigDecimal.valueOf(25));

        stone.setInventory(40);

        assertEquals(40, stone.getInventory());
    }

    @Test
    public void setPrice_updatesValue() {
        Stone stone = new Stone(1, "Diamond", 1, BigDecimal.valueOf(25));

        stone.setPrice(BigDecimal.valueOf(40));

        assertEquals(BigDecimal.valueOf(40), stone.getPrice());
    }

    @Test
    public void setStoneName_allowsNull() {
        Stone stone = new Stone(1, "Diamond", 1, BigDecimal.valueOf(25));

        stone.setStoneName(null);

        assertNull(stone.getStoneName());
    }
}
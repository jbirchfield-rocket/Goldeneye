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

import com.goldeneye.dto.StoneDTO;
import com.goldeneye.rings.RingsApplication;
 
/**
 *
 * @author scanales
 */

@SpringBootTest(classes = RingsApplication.class)
@DisplayName("Attribute Service Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Tag("integration")
public class StoneServiceTests {

    @Autowired
    private StoneService stoneService;

    @Test
    void getStonesReturnsListOfStoneDTOs() {
        // Stone Attributes: Id, Name, Inventory, Multiplier
        List<StoneDTO> stones = stoneService.getAllStones();
        assertNotNull(stones);
        assertFalse(stones.isEmpty());
        assertTrue(stones.stream().allMatch(s -> s instanceof StoneDTO));
        assertEquals(7, stones.size());
        assertTrue(stones.stream().anyMatch(s -> s.getStoneId() == 1 && s.getName().equals("None")));
        assertTrue(stones.stream().anyMatch(s -> s.getStoneId() == 7 && s.getName().equals("Natural diamond")));
    }
}
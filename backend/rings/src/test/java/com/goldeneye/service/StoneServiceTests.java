/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.goldeneye.service;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.goldeneye.dto.StoneDTO;
import com.goldeneye.exception.ResourceNotFoundException;
import com.goldeneye.model.Stone;
import com.goldeneye.repo.StoneRepo;

/**
 *
 * @author scanales
 */

@DisplayName("Stone Service Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
public class StoneServiceTests {
    @Mock
    private StoneRepo stoneRepo;

    @InjectMocks
    private StoneService stoneService;

    private Stone stoneObj1;
    private Stone stoneObj2;

    @BeforeEach
    void setupStones() {
        stoneObj1 = new Stone(1, "None", 9, BigDecimal.ZERO);
        stoneObj2 = new Stone(7, "Natural diamond", 1, new BigDecimal("1000.00"));
    }

    @Test
    void getAllStonesReturnsListOfStoneDTOs() {
        when(stoneRepo.findAll()).thenReturn(List.of(stoneObj1, stoneObj2));

        // Stone Attributes: Id, Name, Inventory, Price
        List<StoneDTO> stones = stoneService.getAllStones();
        assertNotNull(stones);
        assertFalse(stones.isEmpty());
        assertTrue(stones.stream().allMatch(s -> s instanceof StoneDTO));
        assertEquals(2, stones.size());
        assertTrue(stones.stream().anyMatch(s -> s.getStoneId() == 1 && s.getName().equals("None")));
        assertTrue(stones.stream().anyMatch(s -> s.getStoneId() == 7 && s.getName().equals("Natural diamond")));
    }

    @Test
    void getStoneByIdReturnsCorrectStoneDTO() {
        when(stoneRepo.findByStoneId(1)).thenReturn(stoneObj1);

        StoneDTO stone = stoneService.getStoneById(1);
        assertNotNull(stone);
        assertEquals(1, stone.getStoneId());
        assertEquals("None", stone.getName());
        assertEquals(9, stone.getInventory());
        assertEquals(0, stone.getPrice().compareTo(BigDecimal.ZERO));
    }

    @Test
    void getStoneByIdThrowsResourceNotFoundExceptionForInvalidId() {
        when(stoneRepo.findByStoneId(1)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            stoneService.getStoneById(1);
        });
    }
}
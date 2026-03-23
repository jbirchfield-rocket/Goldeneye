/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.goldeneye.service;

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

import com.goldeneye.dto.WidthDTO;
import com.goldeneye.exception.ResourceNotFoundException;
import com.goldeneye.model.Width;
import com.goldeneye.repo.WidthRepo;
 
/**
 *
 * @author scanales
 */

@DisplayName("Width Service Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
public class WidthServiceTests {
    @Mock
    private WidthRepo widthRepo;

    @InjectMocks
    private WidthService widthService;

    private Width widthObj1;
    private Width widthObj2;

    @BeforeEach
    void setupWidths() {
        widthObj1 = new Width(1, 2, 0.9f, 1);
        widthObj2 = new Width(4, 8, 0.7f, 3);
    }

    @Test
    void getWidthsReturnsListOfWidthDTOs() {
        when(widthRepo.findAll()).thenReturn(List.of(widthObj1, widthObj2));

        // Width Attributes: Id, Name, Multiplier, MaterialUse
        List<WidthDTO> widths = widthService.getAllWidths();
        assertNotNull(widths);
        assertFalse(widths.isEmpty());
        assertTrue(widths.stream().allMatch(w -> w instanceof WidthDTO));
        assertEquals(2, widths.size());
        assertTrue(widths.stream().anyMatch(w -> w.getWidthId() == 1 && w.getWidth() == 2));
        assertTrue(widths.stream().anyMatch(w -> w.getWidthId() == 4 && w.getWidth() == 8));
    }

    @Test
    void getWidthByIdReturnsCorrectWidthDTO() {
        when(widthRepo.findByWidthId(1)).thenReturn(widthObj1);

        WidthDTO width = widthService.getWidthById(1);
        assertNotNull(width);
        assertEquals(1, width.getWidthId());
        assertEquals(2, width.getWidth());
        assertEquals(0.9f, width.getMultiplier());
        assertEquals(1, width.getMaterialUse());
    }

    @Test
    void getWidthByIdThrowsResourceNotFoundExceptionForInvalidId() {
        when(widthRepo.findByWidthId(1)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            widthService.getWidthById(1);
        });
    }
}
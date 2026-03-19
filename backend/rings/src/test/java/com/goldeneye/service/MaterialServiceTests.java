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
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.goldeneye.dto.MaterialDTO;
import com.goldeneye.exception.ResourceNotFoundException;
import com.goldeneye.model.Material;
import com.goldeneye.repo.MaterialRepo;
 
/**
 *
 * @author scanales
 */

@DisplayName("Material Service Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
public class MaterialServiceTests {
    @Mock
    private MaterialRepo materialRepo;

    @InjectMocks
    private MaterialService materialService;

    private Material materialObj1;
    private Material materialObj2;

    private MaterialDTO materialDTO1;
    private MaterialDTO materialDTO2;

    @BeforeEach
    void setupMaterials() {
        materialObj1 = new Material(1, "Gold Plated", 71, 0.500f);
        materialObj2 = new Material(4, "Platinum", 15, 1.000f);
    }

    @Test
    void getAllMaterialsReturnsListOfMaterialDTOs() {
        when(materialRepo.findAll()).thenReturn(List.of(materialObj1, materialObj2));

        // Material Attributes: Id, Name, Inventory, Multiplier
        List<MaterialDTO> materials = materialService.getAllMaterials();
        assertNotNull(materials);
        assertFalse(materials.isEmpty());
        assertEquals(2, materials.size());
        assertTrue(materials.stream().anyMatch(m -> m.getMaterialId() == 1));
        assertTrue(materials.stream().anyMatch(m -> m.getMaterialId() == 4));
    }

    @Test
    void getMaterialByIdReturnsCorrectMaterial() {
        when(materialRepo.findById(1)).thenReturn(materialObj1);

        MaterialDTO material = materialService.getMaterialById(1);
        assertNotNull(material);
        assertEquals(1, material.getMaterialId());
        assertEquals("Gold Plated", material.getName());
        assertEquals(71, material.getInventory());
        assertEquals(0.500f, material.getMultiplier());
    }

    @Test
    void getMaterialByIdThrowsExceptionForInvalidId() {
        when(materialRepo.findById(1)).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> materialService.getMaterialById(1));
    }
}
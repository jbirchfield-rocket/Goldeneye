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

import com.goldeneye.dto.MaterialDTO;
import com.goldeneye.rings.RingsApplication;
 
/**
 *
 * @author scanales
 */

@SpringBootTest(classes = RingsApplication.class)
@DisplayName("Material Service Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Tag("integration")
public class MaterialServiceTests {

    @Autowired
    private MaterialService materialService;

    @Test
    void getAllMaterialsReturnsListOfMaterialDTOs() {
        // Material Attributes: Id, Name, Inventory, Multiplier
        List<MaterialDTO> materials = materialService.getAllMaterials();
        assertNotNull(materials);
        assertFalse(materials.isEmpty());
        assertTrue(materials.stream().allMatch(m -> m instanceof MaterialDTO));
        assertEquals(4, materials.size());
        assertTrue(materials.stream().anyMatch(m -> m.getMaterialId() == 1 && m.getName().equals("Gold Plated")));
        assertTrue(materials.stream().anyMatch(m -> m.getMaterialId() == 4 && m.getName().equals("Platinum")));
    }
}
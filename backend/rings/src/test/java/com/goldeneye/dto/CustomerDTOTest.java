/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.goldeneye.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

/**
 *
 * @author kwall
 */
@DisplayName("Customer DTO Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class CustomerDTOTest {

    @Test
    void noArgConstructorCreatesInstance() {
        CustomerDTO dto = new CustomerDTO();
        assertNotNull(dto);
        assertNull(dto.getCustId());
        assertNull(dto.getName());
    }

    @Test
    void constructorInitializesAllFields() {
        CustomerDTO dto = new CustomerDTO(1, "Jane Doe");
        assertNotNull(dto);
        assertEquals(1, dto.getCustId());
        assertEquals("Jane Doe", dto.getName());
    }

    @Test
    void settersUpdateFields() {
        CustomerDTO dto = new CustomerDTO();
        dto.setCustId(42);
        dto.setName("John Smith");

        assertEquals(42, dto.getCustId());
        assertEquals("John Smith", dto.getName());
    }
}
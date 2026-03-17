package com.goldeneye.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.goldeneye.dto.MaterialDTO;
import com.goldeneye.model.Material;
import com.goldeneye.repo.MaterialRepo;

/**
 *
 * @author dshelby, scanalesR
 */
@Service
public class MaterialService {

    private final MaterialRepo materialRepo;

    public MaterialService(MaterialRepo materialRepo) {
        this.materialRepo = materialRepo;
    }

    public List<MaterialDTO> getAllMaterials() {
        return materialRepo.findAll()
            .stream()
            .map(c -> new MaterialDTO(c.getMaterialId(), c.getMaterialName(), c.getInventory(), c.getMultiplier()))
            .toList();
    }

    public MaterialDTO getMaterialById(int id) {
        Material material = materialRepo.findById(id);
        return new MaterialDTO(
            material.getMaterialId(),
            material.getMaterialName(), 
            material.getInventory(), 
            material.getMultiplier()
        );
    }
}
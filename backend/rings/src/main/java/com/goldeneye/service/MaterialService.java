package com.goldeneye.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.goldeneye.dto.MaterialDTO;
import com.goldeneye.model.Material;
import com.goldeneye.exception.ResourceNotFoundException;
import com.goldeneye.repo.MaterialRepo;

/**
 *
 * @author dshelby, scanalesR
 */
@Service
public class MaterialService {

    private static final Logger logger = LoggerFactory.getLogger(MaterialService.class);

    private final MaterialRepo materialRepo;

    public MaterialService(MaterialRepo materialRepo) {
        this.materialRepo = materialRepo;
    }

    public List<MaterialDTO> getAllMaterials() {
        logger.info("Fetching all materials");
        List<MaterialDTO> materials = materialRepo.findAll()
            .stream()
            .map(c -> new MaterialDTO(c.getMaterialId(), c.getMaterialName(), c.getInventory(), c.getMultiplier()))
            .toList();
        logger.debug("Retrieved {} materials", materials.size());
        return materials;
    }

    public MaterialDTO getMaterialById(int id) {
        logger.info("Fetching material with ID: {}", id);
        Material material = materialRepo.findById(id);
        if (material == null) {
            logger.warn("Material not found with ID: {}", id);
            throw new ResourceNotFoundException("Material not found with id: " + id);
        }
        logger.debug("Found material: {}", material.getMaterialName());
        return new MaterialDTO(
            material.getMaterialId(),
            material.getMaterialName(), 
            material.getInventory(), 
            material.getMultiplier()
        );
    }
}
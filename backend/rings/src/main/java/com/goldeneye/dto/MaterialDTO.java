package com.goldeneye.dto;


/**
 *
 * @author dshelby
 */

public class MaterialDTO {
    private int materialId;
    private String name;
    private int inventory;
    private float multiplier;

    public MaterialDTO(int materialId, String name, int inventory, float multiplier) {
        this.materialId = materialId;
        this.name = name;
        this.inventory = inventory;
        this.multiplier = multiplier;
    }

    public int getMaterialId() { 
        return materialId; 
    }

    public String getName() { 
        return name; 
    }

    public int getInventory() {
        return inventory;
    }

    public float getMultiplier() {
        return multiplier;
    }

}

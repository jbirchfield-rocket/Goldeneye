package com.goldeneye.dto;


/**
 *
 * @author dshelby
 */

public class WidthDTO {
    private int widthId;
    private int width;
    private float multiplier;
    private int materialUse;

    public WidthDTO(int widthId, int width, float multiplier, int materialUse) {
        this.widthId = widthId;
        this.width = width;
        this.multiplier = multiplier;
        this.materialUse = materialUse;
    }

    public int getWidthId() { 
        return widthId; 
    }

    public int getWidth() { 
        return width; 
    }
    
    public float getMultiplier() {
        return multiplier;
    }
    
    public int getMaterialUse() {
        return materialUse;
    }

}

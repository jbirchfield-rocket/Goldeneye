package com.goldeneye.dto;


/**
 *
 * @author dshelby
 */

public class WidthDTO {
    private int widthId;
    private String widthName;
    private float multiplier;
    private int materialUse;

    public WidthDTO(int widthId, String widthName, float multiplier, int materialUse) {
        this.widthId = widthId;
        this.widthName = widthName;
        this.multiplier = multiplier;
        this.materialUse = materialUse;
    }

    public int getWidthId() { 
        return widthId; 
    }

    public String getWidthName() { 
        return widthName; 
    }
    
    public float getMultiplier() {
        return multiplier;
    }
    
    public int getMaterialUse() {
        return materialUse;
    }

}

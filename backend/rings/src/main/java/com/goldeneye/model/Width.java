/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.goldeneye.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 *
 * @author dshelby
 */
@Table("TBWIDTH")
public class Width {
    @Id
    @Column("WID")
    private int widthId;

    @Column("WIDTH")
    private String widthName;

    @Column("MULTIPLIER")
    private float multiplier;

    @Column("MATTUSE")
    private int materialUse;

    public Width(int widthId, String widthName, float multiplier, int materialUse) {
        this.widthId = widthId;
        this.widthName = widthName;
        this.multiplier = multiplier;
        this.materialUse = materialUse;
    }

    public int getWidthId() {
        return widthId;
    }

    public void setWidthId(int widthId) {
        this.widthId = widthId;
    }

    public String getWidthName() {
        return widthName;
    }

    public void setWidthName(String widthName) {
        this.widthName = widthName;
    }

    public float getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(float multiplier) {
        this.multiplier = multiplier;
    }

    public int getMaterialUse() {
        return materialUse;
    }

    public void setMaterialUse(int materialUse) {
        this.materialUse = materialUse;
    }

    
    
}

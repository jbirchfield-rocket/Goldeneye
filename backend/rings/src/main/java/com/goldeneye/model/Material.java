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
@Table("TBMATT")
public class Material {
    @Id
    @Column("MATTID")
    private int materialId;

    @Column("NAME")
    private String materialName;

    @Column("INVENTORY")
    private int inventory;

    @Column("MULTIPLIER")
    private float multiplier;

    public Material(int materialId, String materialName, int inventory, float multiplier) {
        this.materialId = materialId;
        this.materialName = materialName;
        this.inventory = inventory;
        this.multiplier = multiplier;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }
    
    public int getInventory() {
        return inventory;
    }
    
    public void setInventory(int inventory) {
        this.inventory = inventory;
    }
    
        public float getMultiplier() {
            return multiplier;
        }
    
        public void setMultiplier(float multiplier) {
            this.multiplier = multiplier;
        }
}

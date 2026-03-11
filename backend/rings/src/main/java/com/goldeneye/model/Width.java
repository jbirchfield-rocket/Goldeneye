/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.goldeneye.model;

/**
 *
 * @author scanales
 */
public class Width {
        private int widthId;
    private String widthName;
    private float multiplier;
    private int inventory;

    public Width(int widthId, String widthName, float multiplier, int inventory) {
        this.widthId = widthId;
        this.widthName = widthName;
        this.multiplier = multiplier;
        this.inventory = inventory;
    }

    public int getwidthId() {
        return widthId;
    }

    public void setwidthId(int widthId) {
        this.widthId = widthId;
    }

    public String getwidthName() {
        return widthName;
    }

    public void setwidthName(String widthName) {
        this.widthName = widthName;
    }

    public float getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(float multiplier) {
        this.multiplier = multiplier;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }
}

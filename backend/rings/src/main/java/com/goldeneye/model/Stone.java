/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.goldeneye.model;

/**
 *
 * @author scanales
 */
public class Stone {
    private int stoneId;
    private String stoneName;
    private float multiplier;
    private int inventory;

    public Stone(int stoneId, String stoneName, float multiplier, int inventory) {
        this.stoneId = stoneId;
        this.stoneName = stoneName;
        this.multiplier = multiplier;
        this.inventory = inventory;
    }

    public int getstoneId() {
        return stoneId;
    }

    public void setstoneId(int stoneId) {
        this.stoneId = stoneId;
    }

    public String getstoneName() {
        return stoneName;
    }

    public void setstoneName(String stoneName) {
        this.stoneName = stoneName;
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

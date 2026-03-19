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
 * @author scanales
 */

@Table("TBCUST")
public class Customer {
    @Id
    @Column("CUSTID")
    private Integer custId;

    @Column("NAME")
    private String name;

    @Column("ACTIVE")
    private int active;

    public Customer(Integer custId, String name, int active) {
        this.custId = custId;
        this.name = name;
        this.active = active;
    }

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    } 

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}

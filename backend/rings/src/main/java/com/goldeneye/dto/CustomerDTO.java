package com.goldeneye.dto;

/**
 *
 * @author dshelby
 */

public class CustomerDTO {
    private Integer custId;
    private String name;
    private int active;

    public CustomerDTO() {
    }

    public CustomerDTO(Integer custId, String name, int active) {
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

package com.example.LiquebaseJwtSecurityCRUDExample.models;

import java.io.Serializable;

public class HouseResidentId implements Serializable {
    
    private Long house;
    private Long resident;

    public HouseResidentId(){}

    public HouseResidentId(Long house, Long resident){
        this.house = house;
        this.resident = resident;
    }

    //get
    public Long getHouse() { return house; }
    public Long getResident() { return resident; }

    //set
    public void setHouse(Long house) { this.house = house; }
    public void setResident(Long resident) { this.resident = resident; }

}

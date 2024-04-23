package com.example.LiquebaseJwtSecurityCRUDExample.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "house_residents")
@IdClass(HouseResidentId.class)
public class HouseResident {
    
    @Id
    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    @Id
    @ManyToOne
    @JoinColumn(name = "resident_id", referencedColumnName = "user_id")
    private User resident;

    public HouseResident(){}

    public HouseResident(House house, User resident){
        this.house = house;
        this.resident = resident;
    }

    //get
    public House getHouse() { return house; }
    public User getResident() { return resident; }
    
    //set
    public void setHouse(House house) { this.house = house; }
    public void setResident(User resident) { this.resident = resident; }

}

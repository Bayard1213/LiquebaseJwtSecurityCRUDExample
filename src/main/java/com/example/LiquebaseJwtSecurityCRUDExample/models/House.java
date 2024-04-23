package com.example.LiquebaseJwtSecurityCRUDExample.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.FetchType;

@Entity
@Table(name = "houses")
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "house_id_gen")
    @SequenceGenerator(name = "house_id_gen", sequenceName = "houses_house_id_seq", allocationSize = 1)
    @Column(name = "house_id")
    private Long houseId;
    
    @Column(name = "address", nullable = false, length = 100)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HouseResident> residents = new HashSet<>();

    public House(){}

    //get
    public Long getHouseId() { return houseId; }
    public String getAddress() { return address; }
    public User getOwner() { return owner; }

    //set
    public void setAddress(String address) { this.address = address; }
    public void setOwner(User owner) { this.owner = owner; }
 
    public void setResidents(Set<HouseResident> residents) {
        this.residents.clear();
        if (residents !=null) {
            this.residents.addAll(residents);
        }
    }

}

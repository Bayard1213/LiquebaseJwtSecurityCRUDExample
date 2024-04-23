package com.example.LiquebaseJwtSecurityCRUDExample.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.LiquebaseJwtSecurityCRUDExample.models.HouseResident;
import com.example.LiquebaseJwtSecurityCRUDExample.models.HouseResidentId;

@Repository
public interface HouseResidentRepository extends JpaRepository<HouseResident, HouseResidentId> {
    
}

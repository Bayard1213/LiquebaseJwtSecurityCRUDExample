package com.example.LiquebaseJwtSecurityCRUDExample.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.LiquebaseJwtSecurityCRUDExample.models.House;
import com.example.LiquebaseJwtSecurityCRUDExample.models.User;

import java.util.List;


@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
    
    List<House> findByOwner(User owner);
    void deleteByHouseId (Long house_id);

}

package com.example.LiquebaseJwtSecurityCRUDExample.services;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.LiquebaseJwtSecurityCRUDExample.models.House;
import com.example.LiquebaseJwtSecurityCRUDExample.models.HouseResident;
import com.example.LiquebaseJwtSecurityCRUDExample.models.HouseResidentId;
import com.example.LiquebaseJwtSecurityCRUDExample.models.User;
import com.example.LiquebaseJwtSecurityCRUDExample.repositories.HouseRepository;
import com.example.LiquebaseJwtSecurityCRUDExample.repositories.HouseResidentRepository;
import com.example.LiquebaseJwtSecurityCRUDExample.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class HouseService {
    
    private HouseRepository houseRepository;
    private UserRepository userRepository;
    private HouseResidentRepository houseResidentRepository;

    public HouseService(HouseRepository houseRepository, UserRepository userRepository, HouseResidentRepository houseResidentRepository){
        this.houseRepository = houseRepository;
        this.userRepository = userRepository;
        this.houseResidentRepository = houseResidentRepository;
    }

    public List<House> getAllHouses(){
        return houseRepository.findAll();
    }

    public House getHouseById(Long houseId){
        return houseRepository.findById(houseId)
            .orElseThrow(() -> new EntityNotFoundException("House not found: " + houseId));
    }

    public House createHouse(House houseDetails, Long ownerId){
        House house = new House();
        house.setAddress(houseDetails.getAddress());
        if (ownerId !=null) {
            User user = userRepository.findById(ownerId).get();
            house.setOwner(user);
        }
        return houseRepository.save(house);
    }

    @Transactional
    public House updateHouseByHouseId(Long houseId, House houseDetails){
        return houseRepository.findById(houseDetails.getHouseId()).map(
            house ->{
            house.setAddress(houseDetails.getAddress());
            house.setOwner(houseDetails.getOwner());
            return houseRepository.save(house);
        }).orElseThrow(() -> new EntityNotFoundException("House not found: " + houseId));
    }

    @Transactional
    public void deleteHouseByHouseId(Long houseId){
        House house = houseRepository.findById(houseId)
            .orElseThrow(() -> new EntityNotFoundException("House not found: " + houseId));
            houseRepository.delete(house);
    }

    public void addHouseResidents(Long houseId, Long residentId, Authentication auth){
        House house = houseRepository.findById(houseId)
            .orElseThrow(() -> new EntityNotFoundException("House not found: " + houseId));
        
            User owner = userRepository.findById(house.getOwner().getUserId())
            .orElseThrow(() -> new EntityNotFoundException("User not found: " + residentId));
        
            if (!auth.getName().equals(owner.getUsername())) {
            throw new AccessDeniedException("Only owner can add residents");
        }

        User residentUser = userRepository.findById(residentId)
            .orElseThrow(() -> new EntityNotFoundException("User not found: " + residentId));

        HouseResident houseResident = new HouseResident(house, residentUser);
        houseResidentRepository.save(houseResident);

    }

    @Transactional
    public void deleteHouseResidents(Long houseId, Long residentId, Authentication auth){
        House house = houseRepository.findById(houseId)
            .orElseThrow(() -> new EntityNotFoundException("House not found: " + houseId));
        
        User owner = userRepository.findById(house.getOwner().getUserId())
            .orElseThrow(() -> new EntityNotFoundException("User not found: " + residentId));
        
        if (!auth.getName().equals(owner.getUsername())) {
            throw new AccessDeniedException("Only owner can delete residents");
        }
        
        HouseResidentId houseResidentId = new HouseResidentId(houseId, residentId);
        if (houseResidentRepository.existsById(houseResidentId)) {
            houseResidentRepository.deleteById(houseResidentId);
        } else {
            throw new EntityNotFoundException("Resident: " + residentId + "not found in house: " + houseId);
        }
    }

}

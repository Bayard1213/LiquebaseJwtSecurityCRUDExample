package com.example.LiquebaseJwtSecurityCRUDExample.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.LiquebaseJwtSecurityCRUDExample.models.House;
import com.example.LiquebaseJwtSecurityCRUDExample.repositories.HouseRepository;
import com.example.LiquebaseJwtSecurityCRUDExample.services.HouseService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/house/")
public class HouseController {
    
    HouseRepository houseRepository;
    HouseService houseService;

    public HouseController(HouseRepository houseRepository, HouseService houseService){
        this.houseRepository = houseRepository;
        this.houseService = houseService;
    }

    @GetMapping("/get_all")
    public List<House> getAllHouses() {
        return houseRepository.findAll();
    }

    @GetMapping("/get_by_id/{id}")
    public ResponseEntity<?> getHosueById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(houseService.getHouseById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> putMethodName(@PathVariable Long id, @RequestBody House house) {
        try {
            return ResponseEntity.ok(houseService.updateHouseByHouseId(id, house));            
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteHouseById(@PathVariable Long id){
        try {
            houseService.deleteHouseByHouseId(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> postMethodName(@RequestBody House house, @RequestParam Long ownerId) {
        try {
            return ResponseEntity.ok(houseService.createHouse(house, ownerId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }        
    }

    @PostMapping("/{houseId}/residents/add")
    public ResponseEntity<?> addResident(
        @PathVariable Long houseId,
        @RequestParam Long residentId,
        Authentication auth) {
            try {
                if (auth == null) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
                houseService.addHouseResidents(houseId, residentId, auth);
                return ResponseEntity.ok().build();
            } catch (EntityNotFoundException e) {
                e.printStackTrace();
                return ResponseEntity.notFound().build();
            } catch (AccessDeniedException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            
    }

    @DeleteMapping("/{houseId}/residents/delete")
    public ResponseEntity<?> deleteResident(
        @PathVariable Long houseId,
        @RequestParam Long residentId,
        Authentication auth
    ){
        try {
            if (auth == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            houseService.deleteHouseResidents(houseId, residentId, auth);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        } catch (AccessDeniedException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

}
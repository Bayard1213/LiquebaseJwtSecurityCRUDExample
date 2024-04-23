package com.example.LiquebaseJwtSecurityCRUDExample.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.LiquebaseJwtSecurityCRUDExample.models.User;
import com.example.LiquebaseJwtSecurityCRUDExample.services.UserService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/user/")
public class UserController {
    
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/get_all")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/get_by_id/{id}")
    public ResponseEntity<?> getUsersByUsersId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getUserByUserId(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/get_by_name/{username}")
    public ResponseEntity<?> getMethodName(@PathVariable String username) {
        try {
            return ResponseEntity.ok(userService.getUserByUsername(username)); 
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/update/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody User user) {
        try {
            return ResponseEntity.ok(userService.updateUserByUsername(username, user));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username){
        try {
            userService.getUserByUsername(username);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}

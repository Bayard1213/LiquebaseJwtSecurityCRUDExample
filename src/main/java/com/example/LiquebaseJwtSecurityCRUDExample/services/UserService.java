package com.example.LiquebaseJwtSecurityCRUDExample.services;

import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.LiquebaseJwtSecurityCRUDExample.models.Role;
import com.example.LiquebaseJwtSecurityCRUDExample.models.User;
import com.example.LiquebaseJwtSecurityCRUDExample.repositories.RoleRepository;
import com.example.LiquebaseJwtSecurityCRUDExample.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
    
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserByUserId(Long userId){
        return userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException("User not found: " + username));
    }

    @Transactional
    public User updateUserByUsername(String username, User userDetails){
        return userRepository.findByUsername(username).map(
            user -> {
                user.setUsername(userDetails.getUsername());
                if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                    String encodedPassword = passwordEncoder.encode(userDetails.getPassword());
                    user.setPassword(encodedPassword);
                }

                user.setEnabled(userDetails.getEnabled());
                user.setAge(userDetails.getAge());
                user.getRoles().clear();
                
                Set<Role> updateRoles = userDetails.getRoles();

                if (updateRoles != null) {
                    updateRoles.forEach(role -> {
                        Role roleEntity = roleRepository.findById(role.getRoleId())
                            .orElseThrow(() -> new EntityNotFoundException("Role not found: " + role.getRoleId()));
                        user.getRoles().add(roleEntity); 
                    });
                }
                
                return userRepository.save(user);

            }).orElseThrow(() -> new EntityNotFoundException("User not found: " + username));
    }

    @Transactional
    public void deleteUserByUsername(String username){
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException("User not found: " + username));
        userRepository.delete(user);
    }

}

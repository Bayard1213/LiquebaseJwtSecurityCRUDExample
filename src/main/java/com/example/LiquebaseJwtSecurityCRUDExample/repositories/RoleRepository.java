package com.example.LiquebaseJwtSecurityCRUDExample.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.LiquebaseJwtSecurityCRUDExample.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    Optional<Role> findByRoleName(String roleName);
    Optional<Role> findByRoleId(Long roleId);

}

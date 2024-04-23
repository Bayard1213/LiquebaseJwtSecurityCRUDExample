package com.example.LiquebaseJwtSecurityCRUDExample.models;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(name = "role_name", nullable = false, length = 50)
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public Role(){}

    public Role(Long role_id, String username){
        this.roleId = role_id;
    }

    //get
    public Long getRoleId(){ return roleId; }
    public String getRoleName() { return roleName; }
    
    @Override
    public String getAuthority() {
        return roleName;
    }

    //set
    public void setRoleName(String roleName) { this.roleName = roleName; }

}

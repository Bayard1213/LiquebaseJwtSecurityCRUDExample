package com.example.LiquebaseJwtSecurityCRUDExample.authentication;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterRequest {
    
    private String username;
    private String password;
    private int age;

    @JsonProperty("roles")
    private Set<String> roleName;
    
    private boolean enabled;

    public RegisterRequest() {}

    //get
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public int getAge() { return age; }
    public Set<String> getRolesName() { return roleName; }
    public boolean getEnabled() { return enabled; }

    //set
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setAge(int age) { this.age = age; }
    public void setRoleName(Set<String> roleName) { this.roleName = roleName; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

}

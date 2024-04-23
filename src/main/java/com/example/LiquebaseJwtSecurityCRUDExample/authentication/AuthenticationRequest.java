package com.example.LiquebaseJwtSecurityCRUDExample.authentication;

public class AuthenticationRequest {
    
    private String username;
    private String password;

    //get
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    //set
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }

}

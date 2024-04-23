package com.example.LiquebaseJwtSecurityCRUDExample.authentication;

public class AuthenticationResponse {
    
    private String token;

    public AuthenticationResponse(){}

    //get
    public String getToken() { return token; }

    //set
    public void setToken(String token) { this.token = token; }
    
}

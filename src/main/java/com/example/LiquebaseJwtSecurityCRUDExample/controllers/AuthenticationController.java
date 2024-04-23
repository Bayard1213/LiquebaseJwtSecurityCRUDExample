package com.example.LiquebaseJwtSecurityCRUDExample.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.LiquebaseJwtSecurityCRUDExample.authentication.AuthenticationRequest;
import com.example.LiquebaseJwtSecurityCRUDExample.authentication.AuthenticationResponse;
import com.example.LiquebaseJwtSecurityCRUDExample.authentication.AuthenticationService;
import com.example.LiquebaseJwtSecurityCRUDExample.authentication.RegisterRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth/")
public class AuthenticationController {
    
    private AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> postAuthenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> postRegister(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

}

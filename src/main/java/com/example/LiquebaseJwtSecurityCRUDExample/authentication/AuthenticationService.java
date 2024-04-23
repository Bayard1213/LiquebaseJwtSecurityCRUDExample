package com.example.LiquebaseJwtSecurityCRUDExample.authentication;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.LiquebaseJwtSecurityCRUDExample.models.Role;
import com.example.LiquebaseJwtSecurityCRUDExample.models.User;
import com.example.LiquebaseJwtSecurityCRUDExample.repositories.RoleRepository;
import com.example.LiquebaseJwtSecurityCRUDExample.repositories.UserRepository;
import com.example.LiquebaseJwtSecurityCRUDExample.services.JwtService;

@Service
public class AuthenticationService {
    
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private RoleRepository roleRepository;

    public AuthenticationService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 AuthenticationManager authenticationManager,
                                 RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
    }

    public AuthenticationResponse register(RegisterRequest request){
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAge(request.getAge());

        if (request.getRolesName() != null) {
            Set<Role> userRoles = request.getRolesName().stream()
                .map(roleName -> roleRepository.findByRoleName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
                .collect(Collectors.toSet());
            user.setRoles(userRoles);
        }

        user.setEnabled(request.getEnabled());
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);

        AuthenticationResponse response = new AuthenticationResponse();
        response.setToken(jwtToken);

        return response;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(), 
                request.getPassword()
                )
        );
        
        User user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + request.getUsername()));
        
            String jwtToken = jwtService.generateToken(user);

        AuthenticationResponse response = new AuthenticationResponse();
        response.setToken(jwtToken);

        return response;
    }

}

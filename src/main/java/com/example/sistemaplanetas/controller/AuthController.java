package com.example.sistemaplanetas.controller;

import com.example.sistemaplanetas.dto.ApiResponse;
import com.example.sistemaplanetas.dto.LoginRequest;
import com.example.sistemaplanetas.dto.LoginResponse;
import com.example.sistemaplanetas.dto.UserRequest;
import com.example.sistemaplanetas.entity.User;
import com.example.sistemaplanetas.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            System.out.println("=== DEBUG LOGIN ATTEMPT ===");
            System.out.println("Username: " + request.getUsername());
            System.out.println("Password length: " + request.getPassword().length());
            
            LoginResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // IMPORTANTE: Imprime el error completo
            System.err.println("=== LOGIN ERROR ===");
            System.err.println("Error type: " + e.getClass().getName());
            System.err.println("Error message: " + e.getMessage());
            e.printStackTrace();
            
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(null, null, null, "Error: " + e.getMessage()));
        }
    }
    
    @PostMapping("/register")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody UserRequest request) {
        try {
            User user = authService.createUser(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "User created successfully", user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
}
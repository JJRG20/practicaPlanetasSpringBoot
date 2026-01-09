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
            LoginResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(null, null, null, "Invalid credentials"));
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
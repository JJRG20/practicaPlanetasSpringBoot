package com.example.sistemaplanetas.services;

import com.example.sistemaplanetas.entities.User;
import com.example.sistemaplanetas.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Registro de usuario
     */
    public User register(String username, String rawPassword, User.Role role) {

        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("El usuario ya existe");
        }

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(rawPassword))
                .role(role)
                .build();

        return userRepository.save(user);
    }

    /**
     * Validación de credenciales
     */
    public User authenticate(String username, String rawPassword) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        return user;
    }
}
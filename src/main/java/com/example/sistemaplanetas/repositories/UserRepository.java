package com.example.sistemaplanetas.repositories;

import com.example.sistemaplanetas.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Para login
    Optional<User> findByUsername(String username);

    // Útil para validación al crear usuarios
    boolean existsByUsername(String username);
}
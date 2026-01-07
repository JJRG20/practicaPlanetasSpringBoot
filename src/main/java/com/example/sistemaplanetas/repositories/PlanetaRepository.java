package com.example.sistemaplanetas.repositories;

import com.example.sistemaplanetas.entities.Planeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetaRepository extends JpaRepository<Planeta, Long> {
}
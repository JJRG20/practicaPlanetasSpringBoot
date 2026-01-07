package com.example.sistemaplanetas.repositories;

import com.example.sistemaplanetas.entities.Luna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LunaRepository extends JpaRepository<Luna, Long> {

    // Todas las Lunas asociadas a un Planeta
    List<Luna> findByPlaneta_IdPlanet(Long idPlanet);
}
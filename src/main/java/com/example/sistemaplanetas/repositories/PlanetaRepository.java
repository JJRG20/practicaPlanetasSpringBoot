package com.example.sistemaplanetas.repositories;

import com.example.sistemaplanetas.entities.Planeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanetaRepository extends JpaRepository<Planeta, Long> {

    // Equivalente a: Planeta.findAll()
    @Override
    List<Planeta> findAll();

    // Equivalente a: Planeta.findByPk(idPrime)
    // (ya viene heredado, lo dejo comentado para claridad)
    // Optional<Planeta> findById(Long idPrime);

    // Ejemplo: filtro por valor (DOUBLE)
    List<Planeta> findByValorBetween(Double min, Double max);
}
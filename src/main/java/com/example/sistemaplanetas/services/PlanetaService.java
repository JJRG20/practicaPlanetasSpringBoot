package com.example.sistemaplanetas.services;

import com.example.sistemaplanetas.entities.Planeta;
import com.example.sistemaplanetas.repositories.PlanetaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanetaService {

    private final PlanetaRepository planetaRepository;

    public PlanetaService(PlanetaRepository planetaRepository) {
        this.planetaRepository = planetaRepository;
    }

    public List<Planeta> obtenerTodos() {
        return planetaRepository.findAll();
    }

    public Planeta obtenerPorId(Long idPlanet) {
        return planetaRepository.findById(idPlanet)
                .orElseThrow(() -> new RuntimeException("Planeta no encontrado"));
    }

    public Planeta crear(Planeta planeta) {
        return planetaRepository.save(planeta);
    }

    public Planeta actualizar(Long idPlanet, Planeta datos) {
        Planeta existente = obtenerPorId(idPlanet);
        existente.setValor(datos.getValor());
        return planetaRepository.save(existente);
    }

    public void eliminar(Long idPlanet) {
        Planeta existente = obtenerPorId(idPlanet);
        existente.setActivo(false); // soft delete
        planetaRepository.save(existente);
    }
}

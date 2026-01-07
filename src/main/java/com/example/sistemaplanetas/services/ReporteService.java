package com.example.sistemaplanetas.services;

import com.example.sistemaplanetas.repositories.PlanetaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReporteService {

    private final PlanetaRepository planetaRepository;

    public ReporteService(PlanetaRepository planetaRepository) {
        this.planetaRepository = planetaRepository;
    }

    public List<Object[]> reportePorCantidadLuna(Long min, Long max) {
        return planetaRepository.reporteCantidadLuna(min, max);
    }
}

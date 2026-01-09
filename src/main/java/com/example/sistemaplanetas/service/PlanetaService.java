package com.example.sistemaplanetas.service;

import com.example.sistemaplanetas.dto.PlanetaRequest;
import com.example.sistemaplanetas.dto.PlanetaResponse;
import com.example.sistemaplanetas.dto.LunaResponse;
import com.example.sistemaplanetas.dto.PlanetaReportRequest;
import com.example.sistemaplanetas.entity.Planeta;
import com.example.sistemaplanetas.entity.Luna;
import com.example.sistemaplanetas.repository.PlanetaRepository;
import com.example.sistemaplanetas.repository.LunaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanetaService {
    
    @Autowired
    private PlanetaRepository planetaRepository;
    
    @Autowired
    private LunaRepository lunaRepository;
    
    // Para astros: solo planetas no eliminados
    public List<PlanetaResponse> getAllPlanetasForAstro() {
        return planetaRepository.findByDeletedAtIsNull().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    // Para admins: todos las planetas (incluyendo eliminados)
    public List<PlanetaResponse> getAllPlanetasForAdmin() {
        return planetaRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public PlanetaResponse getPlanetaByIdForAstro(Integer idPlanet) {
        Planeta planeta = planetaRepository.findByIdPlanetAndDeletedAtIsNull(idPlanet)
                .orElseThrow(() -> new RuntimeException("Planeta not found or deleted"));
        return convertToResponse(planeta);
    }
    
    public PlanetaResponse getPlanetaByIdForAdmin(Integer idPlanet) {
        Planeta planeta = planetaRepository.findById(idPlanet)
                .orElseThrow(() -> new RuntimeException("Planeta not found"));
        return convertToResponse(planeta);
    }
    
    public PlanetaResponse createPlaneta(PlanetaRequest request) {
        Planeta planeta = new Planeta();
        planeta.setName(request.getName());
        planeta.setDiameter(request.getDiameter());
        planeta.setWeight(request.getWeight());
        planeta.setSun(request.getSun());
        planeta.setTime(request.getTime());
        
        Planeta savedPlaneta = planetaRepository.save(planeta);
        return convertToResponse(savedPlaneta);
    }
    
    public PlanetaResponse updatePlaneta(Integer idPlanet, PlanetaRequest request) {
        Planeta planeta = planetaRepository.findById(idPlanet)
                .orElseThrow(() -> new RuntimeException("Planeta not found"));
        
        planeta.setName(request.getName());
        planeta.setDiameter(request.getDiameter());
        planeta.setWeight(request.getWeight());
        planeta.setSun(request.getSun());
        planeta.setTime(request.getTime());
        
        Planeta updatedPlaneta = planetaRepository.save(planeta);
        return convertToResponse(updatedPlaneta);
    }
    
    @Transactional
    public void softDeletePlaneta(Integer idPlanet) {
        Planeta planeta = planetaRepository.findById(idPlanet)
                .orElseThrow(() -> new RuntimeException("Planeta not found"));
        
        LocalDateTime now = LocalDateTime.now();
        planeta.setDeletedAt(now);
        planetaRepository.save(planeta);
        
        // Soft delete en cascada para lunas asociadas
        lunaRepository.softDeleteByPlanetaId(idPlanet, now);
    }
    
    public void restorePlaneta(Integer idPlanet) {
        Planeta planeta = planetaRepository.findById(idPlanet)
                .orElseThrow(() -> new RuntimeException("Planeta not found"));
        
        planeta.setDeletedAt(null);
        planetaRepository.save(planeta);
        
        // Las lunas NO se restauran automáticamente
    }
    
    @Transactional
    public void deletePlanetaPermanently(Integer idPlanet) {
        Planeta planeta = planetaRepository.findById(idPlanet)
                .orElseThrow(() -> new RuntimeException("Planeta not found"));
        
        // Eliminar lunas asociadas primero
        lunaRepository.deleteByPlanetaId(idPlanet);
        
        // Luego eliminar el planeta
        planetaRepository.delete(planeta);
    }
    
    // Reporte con filtros
    public List<PlanetaResponse> generateReport(PlanetaReportRequest request, boolean isAdmin) {
        List<Planeta> planetas;
        
        if (isAdmin) {
            planetas = planetaRepository.findByDiameterRangeIncludingDeleted(
                request.getMinDiameter(), 
                request.getMaxDiameter()
            );
        } else {
            planetas = planetaRepository.findByDiameterRange(
                request.getMinDiameter(), 
                request.getMaxDiameter()
            );
        }
        
        // Filtrar por cantidad de lunas si se especifica
        if (request.getMinLunas() != null || request.getMaxLunas() != null) {
            planetas = planetas.stream()
                .filter(planeta -> {
                    long lunaCount = lunaRepository.countActiveLunasByPlanetaId(planeta.getIdPlanet());
                    
                    boolean minCondition = request.getMinLunas() == null || lunaCount >= request.getMinLunas();
                    boolean maxCondition = request.getMaxLunas() == null || lunaCount <= request.getMaxLunas();
                    
                    return minCondition && maxCondition;
                })
                .collect(Collectors.toList());
        }
        
        return planetas.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    private PlanetaResponse convertToResponse(Planeta planeta) {
        PlanetaResponse response = new PlanetaResponse();
        response.setIdPlanet(planeta.getIdPlanet());
        response.setName(planeta.getName());
        response.setDiameter(planeta.getDiameter());
        response.setWeight(planeta.getWeight());
        response.setSun(planeta.getSun());
        response.setTime(planeta.getTime());
        response.setCreatedAt(planeta.getCreatedAt());
        response.setUpdatedAt(planeta.getUpdatedAt());
        response.setDeletedAt(planeta.getDeletedAt());
        
        // Obtener lunas asociadas
        List<Luna> lunas = lunaRepository.findByPlaneta_IdPlanet(planeta.getIdPlanet());
        List<LunaResponse> lunaResponses = lunas.stream()
                .map(this::convertLunaToResponse)
                .collect(Collectors.toList());
        
        response.setLunas(lunaResponses);
        
        return response;
    }
    
    private LunaResponse convertLunaToResponse(Luna luna) {
        LunaResponse response = new LunaResponse();
        response.setIdLuna(luna.getIdLuna());
        response.setName(luna.getName());
        response.setDiameter(luna.getDiameter());
        response.setWeight(luna.getWeight());
        response.setIdPlanet(luna.getPlaneta().getIdPlanet());
        response.setPlanetaName(luna.getPlaneta().getName());
        response.setCreatedAt(luna.getCreatedAt());
        response.setUpdatedAt(luna.getUpdatedAt());
        response.setDeletedAt(luna.getDeletedAt());
        return response;
    }
}
package com.example.sistemaplanetas.service;

import com.example.sistemaplanetas.dto.LunaRequest;
import com.example.sistemaplanetas.dto.LunaResponse;
import com.example.sistemaplanetas.dto.UpdateLunaRequest;
import com.example.sistemaplanetas.dto.UpdatePlanetaIdRequest;
import com.example.sistemaplanetas.entity.Luna;
import com.example.sistemaplanetas.entity.Planeta;
import com.example.sistemaplanetas.repository.LunaRepository;
import com.example.sistemaplanetas.repository.PlanetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LunaService {
    
    @Autowired
    private LunaRepository lunaRepository;
    
    @Autowired
    private PlanetaRepository planetaRepository;
    
    // Para astros: solo lunas no eliminadas
    public List<LunaResponse> getAllLunasForAstro() {
        return lunaRepository.findByDeletedAtIsNull().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    // Para admins: todas las lunas (incluyendo eliminadas)
    public List<LunaResponse> getAllLunasForAdmin() {
        return lunaRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public LunaResponse getLunaByIdForAstro(Integer idLuna) {
        Luna luna = lunaRepository.findByIdLunaAndDeletedAtIsNull(idLuna)
                .orElseThrow(() -> new RuntimeException("Luna not found or deleted"));
        return convertToResponse(luna);
    }
    
    public LunaResponse getLunaByIdForAdmin(Integer idLuna) {
        Luna luna = lunaRepository.findById(idLuna)
                .orElseThrow(() -> new RuntimeException("Luna not found"));
        return convertToResponse(luna);
    }
    
    public LunaResponse createLuna(LunaRequest request) {
        Planeta planeta = planetaRepository.findById(request.getIdPlanet())
                .orElseThrow(() -> new RuntimeException("Planeta not found"));
        
        Luna luna = new Luna();
        luna.setName(request.getName());
        luna.setDiameter(request.getDiameter());
        luna.setWeight(request.getWeight());
        luna.setPlaneta(planeta);
        
        Luna savedLuna = lunaRepository.save(luna);
        return convertToResponse(savedLuna);
    }
    
    public LunaResponse updateLuna(Integer idLuna, LunaRequest request) {
        Luna luna = lunaRepository.findById(idLuna)
                .orElseThrow(() -> new RuntimeException("Luna not found"));
        
        Planeta planeta = planetaRepository.findById(request.getIdPlanet())
                .orElseThrow(() -> new RuntimeException("Planeta not found"));
        
        luna.setName(request.getName());
        luna.setDiameter(request.getDiameter());
        luna.setWeight(request.getWeight());
        luna.setPlaneta(planeta);
        
        Luna updatedLuna = lunaRepository.save(luna);
        return convertToResponse(updatedLuna);
    }
    
    public LunaResponse updatePlanetaId(Integer idLuna, UpdatePlanetaIdRequest request) {
        Luna luna = lunaRepository.findById(idLuna)
                .orElseThrow(() -> new RuntimeException("Luna not found"));
        
        Planeta newPlaneta = planetaRepository.findById(request.getNewIdPlanet())
                .orElseThrow(() -> new RuntimeException("Planeta not found"));
        
        luna.setPlaneta(newPlaneta);
        
        Luna updatedLuna = lunaRepository.save(luna);
        return convertToResponse(updatedLuna);
    }
    
    public void softDeleteLuna(Integer idLuna) {
        Luna luna = lunaRepository.findById(idLuna)
                .orElseThrow(() -> new RuntimeException("Luna not found"));
        
        luna.setDeletedAt(LocalDateTime.now());
        lunaRepository.save(luna);
    }
    
    public void restoreLuna(Integer idLuna) {
        Luna luna = lunaRepository.findById(idLuna)
                .orElseThrow(() -> new RuntimeException("Luna not found"));
        
        luna.setDeletedAt(null);
        lunaRepository.save(luna);
    }
    
    public void deleteLunaPermanently(Integer idLuna) {
        Luna luna = lunaRepository.findById(idLuna)
                .orElseThrow(() -> new RuntimeException("Luna not found"));
        
        lunaRepository.delete(luna);
    }

    // Método nuevo: actualizar luna SIN cambiar planeta
    public LunaResponse updateLunaWithoutPlaneta(Integer idLuna, UpdateLunaRequest request) {
        Luna luna = lunaRepository.findById(idLuna)
                .orElseThrow(() -> new RuntimeException("Luna not found"));
        
        luna.setName(request.getName());
        luna.setDiameter(request.getDiameter());
        luna.setWeight(request.getWeight());
        
        Luna updatedLuna = lunaRepository.save(luna);
        return convertToResponse(updatedLuna);
    }
    
    private LunaResponse convertToResponse(Luna luna) {
        LunaResponse response = new LunaResponse();
        response.setIdLuna(luna.getIdLuna());
        response.setName(luna.getName());
        response.setDiameter(luna.getDiameter());
        response.setWeight(luna.getWeight());
        response.setIdPlanet(luna.getPlaneta().getIdPlanet());
        //response.setPlanetaName(luna.getPlaneta().getName());
        response.setCreatedAt(luna.getCreatedAt());
        response.setUpdatedAt(luna.getUpdatedAt());
        response.setDeletedAt(luna.getDeletedAt());
        return response;
    }
}
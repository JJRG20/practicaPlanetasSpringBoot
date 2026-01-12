package com.example.sistemaplanetas.controller;

import com.example.sistemaplanetas.dto.PlanetaResponse;
import com.example.sistemaplanetas.dto.LunaResponse;
import com.example.sistemaplanetas.dto.PlanetaReportRequest;
import com.example.sistemaplanetas.service.PlanetaService;
import com.example.sistemaplanetas.service.LunaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/astro")
@PreAuthorize("hasAnyRole('astro', 'admin')")
public class AstroController {
    
    @Autowired
    private PlanetaService planetaService;
    
    @Autowired
    private LunaService lunaService;
    
    // ==================== Planetas ====================
    
    @GetMapping("/planetas")
    public ResponseEntity<List<PlanetaResponse>> getAllPlanetas() {
        try {
            List<PlanetaResponse> planetas = planetaService.getAllPlanetasForAstro();
            return ResponseEntity.ok(planetas);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/planetas/{id}")
    public ResponseEntity<PlanetaResponse> getPlanetaById(@PathVariable Integer id) {
        try {
            PlanetaResponse planeta = planetaService.getPlanetaByIdForAstro(id);
            return ResponseEntity.ok(planeta);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/planetas/report")
    public ResponseEntity<List<PlanetaResponse>> generatePlanetaReport(
            @RequestParam(required = false) Double minDiameter,
            @RequestParam(required = false) Double maxDiameter,
            @RequestParam(required = false) Double minWeight,
            @RequestParam(required = false) Double maxWeight,
            @RequestParam(required = false) Double minSunDist,
            @RequestParam(required = false) Double maxSunDist,
            @RequestParam(required = false) Double minTime,
            @RequestParam(required = false) Double maxTime,
            @RequestParam(required = false) Integer minLunas,
            @RequestParam(required = false) Integer maxLunas) {
        try {
            PlanetaReportRequest request = new PlanetaReportRequest(minDiameter, 
                                                                    maxDiameter, 
                                                                    minWeight,
                                                                    maxWeight,
                                                                    minSunDist,
                                                                    maxSunDist,
                                                                    minTime,
                                                                    maxTime,
                                                                    minLunas, maxLunas);
            List<PlanetaResponse> planetas = planetaService.generateReport(request, false);
            return ResponseEntity.ok(planetas);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // ==================== Lunas ====================
    
    @GetMapping("/lunas")
    public ResponseEntity<List<LunaResponse>> getAllLunas() {
        try {
            List<LunaResponse> lunas = lunaService.getAllLunasForAstro();
            return ResponseEntity.ok(lunas);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/lunas/{id}")
    public ResponseEntity<LunaResponse> getLunaById(@PathVariable Integer id) {
        try {
            LunaResponse luna = lunaService.getLunaByIdForAstro(id);
            return ResponseEntity.ok(luna);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
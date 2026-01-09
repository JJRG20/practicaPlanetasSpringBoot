package com.example.sistemaplanetas.controller;

import com.example.sistemaplanetas.dto.*;
import com.example.sistemaplanetas.service.PlanetaService;
import com.example.sistemaplanetas.service.LunaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('admin')")
public class AdminController {
    
    @Autowired
    private PlanetaService planetaService;
    
    @Autowired
    private LunaService lunaService;
    
    // ==================== Planetas - VIEW ====================
    
    @GetMapping("/planetas")
    public ResponseEntity<List<PlanetaResponse>> getAllPlanetas() {
        try {
            List<PlanetaResponse> planetas = planetaService.getAllPlanetasForAdmin();
            return ResponseEntity.ok(planetas);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/planetas/{id}")
    public ResponseEntity<PlanetaResponse> getPlanetaById(@PathVariable Integer id) {
        try {
            PlanetaResponse planeta = planetaService.getPlanetaByIdForAdmin(id);
            return ResponseEntity.ok(planeta);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/planetas/report")
    public ResponseEntity<List<PlanetaResponse>> generatePlanetaReport(
            @RequestParam(required = false) Double minDiameter,
            @RequestParam(required = false) Double maxDiameter,
            @RequestParam(required = false) Integer minLunas,
            @RequestParam(required = false) Integer maxLunas) {
        try {
            PlanetaReportRequest request = new PlanetaReportRequest(minDiameter, maxDiameter, minLunas, maxLunas);
            List<PlanetaResponse> planetas = planetaService.generateReport(request, true);
            return ResponseEntity.ok(planetas);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // ==================== Planetas - CRUD ====================
    
    @PostMapping("/planetas")
    public ResponseEntity<ApiResponse> createPlaneta(@Valid @RequestBody PlanetaRequest request) {
        try {
            PlanetaResponse planeta = planetaService.createPlaneta(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Planeta created successfully", planeta));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
    
    @PutMapping("/planetas/{id}")
    public ResponseEntity<ApiResponse> updatePlaneta(@PathVariable Integer id, 
                                                     @Valid @RequestBody PlanetaRequest request) {
        try {
            PlanetaResponse planeta = planetaService.updatePlaneta(id, request);
            return ResponseEntity.ok(new ApiResponse(true, "Planeta updated successfully", planeta));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
    
    @PatchMapping("/planetas/{id}/soft-delete")
    public ResponseEntity<ApiResponse> softDeletePlaneta(@PathVariable Integer id) {
        try {
            planetaService.softDeletePlaneta(id);
            return ResponseEntity.ok(new ApiResponse(true, "Planeta soft-deleted successfully (with lunas)"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
    
    @PatchMapping("/planetas/{id}/restore")
    public ResponseEntity<ApiResponse> restorePlaneta(@PathVariable Integer id) {
        try {
            planetaService.restorePlaneta(id);
            return ResponseEntity.ok(new ApiResponse(true, "Planeta restored successfully (lunas not restored)"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
    
    @DeleteMapping("/planetas/{id}")
    public ResponseEntity<ApiResponse> deletePlanetaPermanently(@PathVariable Integer id) {
        try {
            planetaService.deletePlanetaPermanently(id);
            return ResponseEntity.ok(new ApiResponse(true, "Planeta deleted permanently (with lunas)"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
    
    // ==================== Lunas - VIEW ====================
    
    @GetMapping("/lunas")
    public ResponseEntity<List<LunaResponse>> getAllLunas() {
        try {
            List<LunaResponse> lunas = lunaService.getAllLunasForAdmin();
            return ResponseEntity.ok(lunas);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/lunas/{id}")
    public ResponseEntity<LunaResponse> getLunaById(@PathVariable Integer id) {
        try {
            LunaResponse luna = lunaService.getLunaByIdForAdmin(id);
            return ResponseEntity.ok(luna);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // ==================== Lunas - CRUD ====================
    
    @PostMapping("/lunas")
    public ResponseEntity<ApiResponse> createLuna(@Valid @RequestBody LunaRequest request) {
        try {
            LunaResponse luna = lunaService.createLuna(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Luna created successfully", luna));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
    
    @PutMapping("/lunas/{id}")
    public ResponseEntity<ApiResponse> updateLuna(@PathVariable Integer id, 
                                                    @Valid @RequestBody UpdateLunaRequest request) {
        try {
            LunaResponse luna = lunaService.updateLunaWithoutPlaneta(id, request);
            return ResponseEntity.ok(new ApiResponse(true, "Luna updated successfully", luna));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
    
    @PatchMapping("/lunas/{id}/planeta")
    public ResponseEntity<ApiResponse> updateLunaPlaneta(@PathVariable Integer id, 
                                                          @Valid @RequestBody UpdatePlanetaIdRequest request) {
        try {
            LunaResponse luna = lunaService.updatePlanetaId(id, request);
            return ResponseEntity.ok(new ApiResponse(true, "Luna planeta updated successfully", luna));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
    
    @PatchMapping("/lunas/{id}/soft-delete")
    public ResponseEntity<ApiResponse> softDeleteLuna(@PathVariable Integer id) {
        try {
            lunaService.softDeleteLuna(id);
            return ResponseEntity.ok(new ApiResponse(true, "Luna soft-deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
    
    @PatchMapping("/lunas/{id}/restore")
    public ResponseEntity<ApiResponse> restoreLuna(@PathVariable Integer id) {
        try {
            lunaService.restoreLuna(id);
            return ResponseEntity.ok(new ApiResponse(true, "Luna restored successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
    
    @DeleteMapping("/lunas/{id}")
    public ResponseEntity<ApiResponse> deleteLunaPermanently(@PathVariable Integer id) {
        try {
            lunaService.deleteLunaPermanently(id);
            return ResponseEntity.ok(new ApiResponse(true, "luna deleted permanently"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
}
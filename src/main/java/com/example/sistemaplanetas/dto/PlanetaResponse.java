package com.example.sistemaplanetas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanetaResponse {
    
    private Integer idPlanet;
    private String name;
    private Double diameter;
    private Double weight;
    private Double sunDist;
    private Double time;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private List<LunaResponse> lunas;
}
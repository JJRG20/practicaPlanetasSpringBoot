package com.example.sistemaplanetas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanetaReportRequest {
    
    private Double minDiameter;
    private Double maxDiameter;
    private Double minWeight;
    private Double maxWeight;
    private Double minSunDist;
    private Double maxSunDist;
    private Double minTime;
    private Double maxTime;
    private Integer minLunas;
    private Integer maxLunas;
}
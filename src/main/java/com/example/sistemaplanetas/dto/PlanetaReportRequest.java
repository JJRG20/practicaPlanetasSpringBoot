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
    private Integer minLunas;
    private Integer maxLunas;
}
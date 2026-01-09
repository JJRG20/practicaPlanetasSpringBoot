package com.example.sistemaplanetas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LunaResponse {
    
    private Integer idLuna;
    private String name;
    private Double diameter;
    private Double weight;
    private Integer idPlanet;
    private String weaponName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
package com.example.sistemaplanetas.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePlanetaIdRequest {
    
    @NotNull(message = "New planeta ID is required")
    private Integer newIdPlanet;
}
package com.example.sistemaplanetas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLunaRequest {
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotNull(message = "Diameter is required")
    @Positive(message = "Diameter must be positive")
    private Double diameter;

    @NotNull(message = "Weight is required")
    @Positive(message = "Weight must be positive")
    private Double weight;
    
    // NO incluye idPlanet
}
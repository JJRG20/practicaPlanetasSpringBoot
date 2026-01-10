package com.example.sistemaplanetas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "planeta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Planeta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPlanet")
    private Integer idPlanet;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private Double diameter;

    @Column(nullable = false)
    private Double weight;

    @Column(name = "sunDist", nullable = false)
    private Double sunDist;

    @Column(nullable = false)
    private Double time;
    

    private LocalDateTime createdAt;
    

    private LocalDateTime updatedAt;
    

    private LocalDateTime deletedAt;
    
    @OneToMany(mappedBy = "planeta", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Luna> lunas;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
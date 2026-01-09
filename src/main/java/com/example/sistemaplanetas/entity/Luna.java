package com.example.sistemaplanetas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.LocalDateTime;

@Entity
@Table(name = "luna")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Luna {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLuna")
    private Integer idLuna;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private Double diameter;

    @Column(nullable = false)
    private Double weight;
    
    @ManyToOne
    @JoinColumn(name = "idPlanet", nullable = false)
    @JsonBackReference
    private Planeta planeta;
    
    @Column(name = "createdAt")
    private LocalDateTime createdAt;
    
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;
    
    @Column(name = "deletedAt")
    private LocalDateTime deletedAt;
    
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

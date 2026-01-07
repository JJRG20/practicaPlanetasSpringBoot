package com.example.sistemaplanetas.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Entity
@Table(name = "planeta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE planeta SET deletedAt = NOW() WHERE idPlanet = ?")
@SQLRestriction("activo = true")
public class Planeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPlanet")
    private Long idPlanet;

    @Column(nullable = false)
    private String name;

    private Double diameter;
    private Double weight;
    private Double distance;
    private Double time;

    @OneToMany(
        mappedBy = "planeta",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Luna> luna;

    @Column(name = "deletedAt")
    private java.time.LocalDateTime deletedAt;
}
package com.example.sistemaplanetas.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "luna")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE luna SET deletedAt = NOW() WHERE idLuna = ?")
@SQLRestriction("activo = true")
public class Luna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLuna")
    private Long idLuna;

    @Column(nullable = false)
    private String name;

    private Double diameter;
    private Double weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPlanet")
    private Planeta planeta;

    @Column(name = "deletedAt")
    private java.time.LocalDateTime deletedAt;
}

package com.example.sistemaplanetas.repository;

import com.example.sistemaplanetas.entity.Planeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlanetaRepository extends JpaRepository<Planeta, Integer> {
    
    // Para astros: solo planetas no eliminados
    List<Planeta> findByDeletedAtIsNull();
    
    Optional<Planeta> findByIdPlanetAndDeletedAtIsNull(Integer idPlanet);
    
    // Para admins: todos los planetas (incluso eliminados)
    @Override
    List<Planeta> findAll();
    
    @Override
    Optional<Planeta> findById(Integer idPlanet);
    
    // Reportes con filtros
    @Query("SELECT p FROM Planeta p WHERE p.deletedAt IS NULL " +
           "AND (:minDiameter IS NULL OR p.diameter >= :minDiameter) " +
           "AND (:maxDiameter IS NULL OR p.diameter <= :maxDiameter)")
    List<Planeta> findByDiameterRange(@Param("minDiameter") Double minDiameter, 
                                   @Param("maxDiameter") Double maxDiameter);
    
    // Para admins: reportes incluyendo eliminados
    @Query("SELECT p FROM Planeta p WHERE p.deletedAt IS NULL " +
           "AND (:minDiameter IS NULL OR p.diameter >= :minDiameter) " +
           "AND (:maxDiameter IS NULL OR p.diameter <= :maxDiameter)")
    List<Planeta> findByDiameterRangeIncludingDeleted(@Param("minDiameter") Double minDiameter, 
                                                    @Param("maxDiameter") Double maxDiameter);
}
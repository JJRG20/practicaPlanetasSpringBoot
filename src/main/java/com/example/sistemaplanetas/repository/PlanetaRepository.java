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
           "AND (:maxDiameter IS NULL OR p.diameter <= :maxDiameter)" +
           "AND (:minWeight IS NULL OR p.weight >= :minWeight) " +
           "AND (:maxWeight IS NULL OR p.weight <= :maxWeight)" +
           "AND (:minSunDist IS NULL OR p.sunDist >= :minSunDist) " +
           "AND (:maxSunDist IS NULL OR p.sunDist <= :maxSunDist)" +
           "AND (:minTime IS NULL OR p.time >= :minTime) " +
           "AND (:maxTime IS NULL OR p.time <= :maxTime)")
    List<Planeta> findByRange(@Param("minDiameter") Double minDiameter, 
                                   @Param("maxDiameter") Double maxDiameter,
                                   @Param("minWeight") Double minWeight, 
                                   @Param("maxWeight") Double maxWeight,
                                   @Param("minSunDist") Double minSunDist, 
                                   @Param("maxSunDist") Double maxSunDist,
                                   @Param("minTime") Double minTime, 
                                   @Param("maxTime") Double maxTime);

    
    // Para admins: reportes incluyendo eliminados
    @Query("SELECT p FROM Planeta p WHERE (:minDiameter IS NULL OR p.diameter >= :minDiameter) " +
           "AND (:maxDiameter IS NULL OR p.diameter <= :maxDiameter)" +
           "AND (:minWeight IS NULL OR p.weight >= :minWeight) " +
           "AND (:maxWeight IS NULL OR p.weight <= :maxWeight)" +
           "AND (:minSunDist IS NULL OR p.sunDist >= :minSunDist) " +
           "AND (:maxSunDist IS NULL OR p.sunDist <= :maxSunDist)" +
           "AND (:minTime IS NULL OR p.time >= :minTime) " +
           "AND (:maxTime IS NULL OR p.time <= :maxTime)")
    List<Planeta> findByRangeIncludingDeleted(@Param("minDiameter") Double minDiameter, 
                                   @Param("maxDiameter") Double maxDiameter,
                                   @Param("minWeight") Double minWeight, 
                                   @Param("maxWeight") Double maxWeight,
                                   @Param("minSunDist") Double minSunDist, 
                                   @Param("maxSunDist") Double maxSunDist,
                                   @Param("minTime") Double minTime, 
                                   @Param("maxTime") Double maxTime);
                                                    
}
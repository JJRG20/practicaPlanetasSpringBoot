package com.example.sistemaplanetas.repository;

import com.example.sistemaplanetas.entity.Luna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LunaRepository extends JpaRepository<Luna, Integer> {
    
    // Para astros: solo lunas no eliminadas
    List<Luna> findByDeletedAtIsNull();
    
    Optional<Luna> findByIdLunaAndDeletedAtIsNull(Integer idLuna);
    
    // Para admins: todas las lunas (incluso eliminadas)
    @Override
    List<Luna> findAll();
    
    @Override
    Optional<Luna> findById(Integer idLuna);
    
    // Lunas por planeta (para astros)
    List<Luna> findByPlaneta_IdPlanetAndDeletedAtIsNull(Integer idPlanet);
    
    // Lunas por planeta (para admins, incluyendo eliminadas)
    List<Luna> findByPlaneta_IdPlanet(Integer idPlanet);
    
    // Soft delete en cascada cuando se elimina un planeta
    @Modifying
    @Query("UPDATE Luna l SET l.deletedAt = :deletedAt WHERE l.planeta.idPlanet = :idPlanet AND l.deletedAt IS NULL")
    void softDeleteByPlanetaId(@Param("idPlanet") Integer idPlanet, @Param("deletedAt") LocalDateTime deletedAt);
    
    // Eliminación permanente en cascada
    @Modifying
    @Query("DELETE FROM Luna l WHERE l.planeta.idPlanet = :idPlanet")
    void deleteByPlanetaId(@Param("idPlanet") Integer idPlanet);
    
    // Contar lunas no eliminadas de un planeta (para reportes)
    @Query("SELECT COUNT(l) FROM Luna l WHERE l.planeta.idPlanet = :idPlanet AND l.deletedAt IS NULL")
    long countActiveLunasByPlanetaId(@Param("idPlanet") Integer idPlanet);
}
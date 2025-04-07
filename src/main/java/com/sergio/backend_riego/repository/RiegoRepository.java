package com.sergio.backend_riego.repository;

import com.sergio.backend_riego.model.Riego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RiegoRepository extends JpaRepository<Riego, Long> {

    @Query("SELECT r FROM Riego r WHERE r.planta.id = :plantaId")
    List<Riego> findByPlantaId(@Param("plantaId") Long plantaId);
}
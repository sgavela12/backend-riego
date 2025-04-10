package com.sergio.backend_riego.repository;

import com.sergio.backend_riego.model.Planta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PlantaRepository extends JpaRepository<Planta, Long> {
    

    // Buscar una planta por su nombre
    Optional<Planta> findByNombre(String nombre);

    @Query("SELECT p FROM Planta p WHERE p.bomba.id = :bombaId")
    Optional<Planta> findByBombaId(@Param("bombaId") Long bombaId);

    @Query("SELECT p FROM Planta p WHERE p.sensor.id = :sensorId")
    Optional<Planta> findBySensorId(@Param("sensorId") Long sensorId);
}


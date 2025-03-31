package com.sergio.backend_riego.repository;

import com.sergio.backend_riego.model.Planta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlantaRepository extends JpaRepository<Planta, Long> {
    // Buscar una planta por el ID del Dispositivo
    Planta findBySensorId(Long sensorId);

    // Buscar una planta por su nombre
    Optional<Planta> findByNombre(String nombre);
}


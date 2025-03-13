package com.sergio.backend_riego.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sergio.backend_riego.model.Planta;

public interface PlantaRepository extends JpaRepository<Planta, Long> {
    Optional<Planta> findByNombre(String nombre);
}


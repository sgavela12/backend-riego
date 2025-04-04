package com.sergio.backend_riego.repository;

import com.sergio.backend_riego.model.Riego;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiegoRepository extends JpaRepository<Riego, Long> {
}
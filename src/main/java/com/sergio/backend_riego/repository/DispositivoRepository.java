package com.sergio.backend_riego.repository;

import com.sergio.backend_riego.model.Dispositivo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DispositivoRepository extends JpaRepository<Dispositivo, Long> {

    Optional<Dispositivo> findByNombre(String nombre);
}

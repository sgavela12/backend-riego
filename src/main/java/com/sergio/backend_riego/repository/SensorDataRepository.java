package com.sergio.backend_riego.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sergio.backend_riego.model.SensorData;

public interface SensorDataRepository extends JpaRepository<SensorData, Long> {

    @Query("SELECT s FROM SensorData s WHERE s.dispositivo.id = :dispositivoId ORDER BY s.fechaHora DESC LIMIT 1")
    Optional<SensorData> findLatestByDispositivoId(@Param("dispositivoId") Long dispositivoId);
}

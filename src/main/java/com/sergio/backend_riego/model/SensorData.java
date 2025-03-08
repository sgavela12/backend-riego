package com.sergio.backend_riego.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sensor_data")
public class SensorData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double valor; // El valor medido por el sensor
    private LocalDateTime fechaHora; // Fecha y hora de la medición
    private String tipoParametro; // Tipo de parámetro medido 
    private String unidad; // Unidad de medida

    @ManyToOne
    @JoinColumn(name = "dispositivo_id")
    private Dispositivo dispositivo; // Relación con el dispositivo (sensor) que generó esta lectura


}

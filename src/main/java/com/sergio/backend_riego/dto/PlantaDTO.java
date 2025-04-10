package com.sergio.backend_riego.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PlantaDTO {
    private Long id;
    private String nombre;
    private String tipo;
    private LocalDateTime ultimoRiego;
    private boolean necesitaAgua;
    private Long bombaId;  
    private Long sensorId; 
}

package com.sergio.backend_riego.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "riego")
public class Riego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaHora; // Fecha y hora del evento de riego
    private Double cantidadAgua; // Cantidad de agua utilizada (en litros, por ejemplo)
    private Integer duracion; // Duración del riego en minutos, por ejemplo
    private String estado; // Estado del riego: "exitoso", "fallido", etc.
    
    @ManyToOne
    @JoinColumn(name = "dispositivo_id")
    private Dispositivo dispositivo; // Dispositivo que realizó el riego 

    @ManyToOne
    @JoinColumn(name = "planta_id")
    private Planta planta; 


}

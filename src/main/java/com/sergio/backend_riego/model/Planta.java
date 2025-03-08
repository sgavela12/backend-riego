package com.sergio.backend_riego.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "planta")
public class Planta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "tipo", nullable = false, length = 50)
    private String tipo;

    @Column(name = "fecha_plantacion", nullable = false)
    private LocalDateTime fechaPlantacion;

    @Column(name = "necesita_agua", nullable = false)
    private boolean necesitaAgua;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dispositivo_id")
    private Dispositivo dispositivo; // Relación con la clase Dispositivo

    

    public void setFechaPlantacion(LocalDateTime fechaPlantacion) {
        this.fechaPlantacion = fechaPlantacion;
    }
}

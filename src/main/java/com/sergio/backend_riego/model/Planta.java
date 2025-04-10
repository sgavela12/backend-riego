package com.sergio.backend_riego.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "planta")
public class Planta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "tipo", nullable = false, length = 50)
    private String tipo;

    @Column(name = "ultimo_riego", nullable = true) 
    private LocalDateTime ultimoRiego;



    @Column(name = "necesita_agua", nullable = false)
    private boolean necesitaAgua;

    // Relación con la entidad Bomba
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bomba_id", nullable = false)
    @ToString.Exclude // Evita la recursión infinita
    @JsonIgnore // Evita la serialización de la bomba en JSON
    private Dispositivo bomba;

    // Relación con la entidad Sensor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_id", nullable = false)
    @ToString.Exclude // Evita la recursión infinita
    @JsonIgnore // Evita la serialización del sensor en JSON
    private Dispositivo sensor;

}

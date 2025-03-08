package com.sergio.backend_riego.model;


import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dispositivos")
public class Dispositivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre; // Nombre del dispositivo
    private String tipo; // Tipo del dispositivo
    private String ubicacion; // Lugar donde está instalado el dispositivo
    private Boolean estado; // Estado: activo/inactivo

    // Relación muchos a muchos con los usuarios
    @ManyToMany
    @JoinTable(
        name = "dispositivos_usuarios", // Nombre de la tabla intermedia
        joinColumns = @JoinColumn(name = "dispositivo_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<User> usuarios; // Lista de usuarios asociados al dispositivo

    // Relación con las lecturas de los sensores (SensorData)
    @OneToMany(mappedBy = "dispositivo")
    private List<SensorData> datos;

    // Relación con los eventos de riego
    @OneToMany(mappedBy = "dispositivo")
    private List<Riego> riegos;
}

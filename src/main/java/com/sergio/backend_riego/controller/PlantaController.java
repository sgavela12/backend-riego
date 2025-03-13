package com.sergio.backend_riego.controller;

import com.sergio.backend_riego.model.Planta;
import com.sergio.backend_riego.service.PlantaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/plantas")
@CrossOrigin(origins = "*") // Permite llamadas desde el frontend
public class PlantaController {

    private final PlantaService plantaService;

    public PlantaController(PlantaService plantaService) {
        this.plantaService = plantaService;
    }

    // Obtener todas las plantas
    @GetMapping
    public List<Planta> obtenerPlantas() {
        return plantaService.getAllPlantas();
    }

    // Obtener una planta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Planta> obtenerPlantaPorId(@PathVariable Long id) {
        Optional<Planta> planta = plantaService.getPlantaById(id);
        return planta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Obtener una planta por nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Planta> obtenerPlantaPorNombre(@PathVariable String nombre) {
        Optional<Planta> planta = plantaService.getPlantaByNombre(nombre);
        return planta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Agregar una nueva planta
    @PostMapping
    public ResponseEntity<Planta> agregarPlanta(@RequestBody Planta planta) {
        Planta nuevaPlanta = plantaService.savePlanta(planta);
        return ResponseEntity.ok(nuevaPlanta);
    }

    // Eliminar una planta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPlanta(@PathVariable Long id) {
        plantaService.deletePlanta(id);
        return ResponseEntity.noContent().build();
    }
}

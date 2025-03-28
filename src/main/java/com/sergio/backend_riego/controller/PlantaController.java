package com.sergio.backend_riego.controller;

import com.sergio.backend_riego.dto.PlantaDTO;
import com.sergio.backend_riego.model.Planta;
import com.sergio.backend_riego.service.PlantaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/plantas")
@CrossOrigin(origins = "*") // Permite llamadas desde el frontend
public class PlantaController {

    private final PlantaService plantaService;

    public PlantaController(PlantaService plantaService) {
        this.plantaService = plantaService;
    }

    // Método para convertir una Planta a PlantaDTO
    private PlantaDTO convertirAPlantaDTO(Planta planta) {
        PlantaDTO dto = new PlantaDTO();
        dto.setId(planta.getId());
        dto.setNombre(planta.getNombre());
        dto.setTipo(planta.getTipo());
        dto.setFechaPlantacion(planta.getFechaPlantacion());
        dto.setHumedad(planta.getHumedad());
        dto.setNecesitaAgua(planta.isNecesitaAgua());
        
        if (planta.getDispositivo() != null) {
            dto.setDispositivoId(planta.getDispositivo().getId());
        }
        return dto;
    }

    // Obtener todas las plantas (como lista de DTOs)
    @GetMapping
    public List<PlantaDTO> obtenerPlantas() {
        List<Planta> plantas = plantaService.getAllPlantas();
        return plantas.stream().map(this::convertirAPlantaDTO).collect(Collectors.toList());
    }

    // Obtener una planta por ID
    @GetMapping("/{id}")
    public ResponseEntity<PlantaDTO> obtenerPlantaPorId(@PathVariable Long id) {
        Optional<Planta> planta = plantaService.getPlantaById(id);
        return planta.map(p -> ResponseEntity.ok(convertirAPlantaDTO(p)))
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Obtener una planta por nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<PlantaDTO> obtenerPlantaPorNombre(@PathVariable String nombre) {
        Optional<Planta> planta = plantaService.getPlantaByNombre(nombre);
        return planta.map(p -> ResponseEntity.ok(convertirAPlantaDTO(p)))
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Agregar una nueva planta
    @PostMapping
    public ResponseEntity<PlantaDTO> agregarPlanta(@RequestBody Planta planta) {
        Planta nuevaPlanta = plantaService.savePlanta(planta);
        return ResponseEntity.ok(convertirAPlantaDTO(nuevaPlanta));
    }

    // Eliminar una planta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPlanta(@PathVariable Long id) {
        plantaService.deletePlanta(id);
        return ResponseEntity.noContent().build();
    }
}

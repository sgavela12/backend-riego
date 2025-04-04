package com.sergio.backend_riego.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sergio.backend_riego.dto.PlantaDTO;
import com.sergio.backend_riego.model.Planta;
import com.sergio.backend_riego.repository.PlantaRepository;

@Service
public class PlantaService {
    @Autowired
    private PlantaRepository plantaRepository;

    // Obtener todas las plantas
    public List<Planta> getAllPlantas() {
        return plantaRepository.findAll();
    }

    // Obtener una planta por ID
    public Optional<Planta> getPlantaById(Long id) {
        return plantaRepository.findById(id);
    }

    // Obtener una planta por nombre
    public Optional<Planta> getPlantaByNombre(String nombre) {
        return plantaRepository.findByNombre(nombre);
    }

    // Crear o actualizar una planta
    public Planta savePlanta(Planta planta) {
        return plantaRepository.save(planta);
    }

    // Eliminar una planta
    public void deletePlanta(Long id) {
        plantaRepository.deleteById(id);
    }

    // Convertir una entidad Planta a un DTO
    public PlantaDTO convertirAPlantaDTO(Planta planta) {
        PlantaDTO dto = new PlantaDTO();
        dto.setId(planta.getId());
        dto.setNombre(planta.getNombre());
        dto.setTipo(planta.getTipo());
        dto.setUltimoRiego(planta.getUltimoRiego());
        dto.setHumedad(planta.getHumedad());
        dto.setNecesitaAgua(planta.isNecesitaAgua());

        // Verificar y asignar los IDs de la bomba y el sensor
        if (planta.getBomba() != null) {
            dto.setBombaId(planta.getBomba().getId());
        } else {
            dto.setBombaId(null); 
        }

        if (planta.getSensor() != null) {
            dto.setSensorId(planta.getSensor().getId());
        } else {
            dto.setSensorId(null); 
        }

        return dto;
    }
}

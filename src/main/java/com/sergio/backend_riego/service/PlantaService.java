package com.sergio.backend_riego.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}

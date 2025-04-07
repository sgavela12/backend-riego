package com.sergio.backend_riego.service;

import com.sergio.backend_riego.model.Riego;
import com.sergio.backend_riego.repository.RiegoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiegoService {

    private final RiegoRepository riegoRepository;

    public RiegoService(RiegoRepository riegoRepository) {
        this.riegoRepository = riegoRepository;
    }

    public Riego registrarRiego(Riego riego) {
        return riegoRepository.save(riego);
    }

    public List<Riego> getRiegosByPlantaId(Long plantaId) {
        return riegoRepository.findByPlantaId(plantaId);
    }
}
package com.sergio.backend_riego.service;

import com.sergio.backend_riego.model.Riego;
import com.sergio.backend_riego.repository.RiegoRepository;
import org.springframework.stereotype.Service;

@Service
public class RiegoService {

    private final RiegoRepository riegoRepository;

    public RiegoService(RiegoRepository riegoRepository) {
        this.riegoRepository = riegoRepository;
    }

    public Riego registrarRiego(Riego riego) {
        return riegoRepository.save(riego);
    }
}
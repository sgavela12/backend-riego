package com.sergio.backend_riego.service;

import com.sergio.backend_riego.model.Dispositivo;
import com.sergio.backend_riego.repository.DispositivoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DispositivoService {

    private final DispositivoRepository dispositivoRepository;

    public DispositivoService(DispositivoRepository dispositivoRepository) {
        this.dispositivoRepository = dispositivoRepository;
    }

    // Obtener todos los dispositivos
    public List<Dispositivo> getAllDispositivos() {
        return dispositivoRepository.findAll();
    }

    // Obtener un dispositivo por ID
    public Optional<Dispositivo> getDispositivoById(Long id) {
        return dispositivoRepository.findById(id);
    }

    // Obtener un dispositivo por nombre
    public Optional<Dispositivo> getDispositivoByNombre(String nombre) {
        return dispositivoRepository.findByNombre(nombre);
    }

    // Guardar un dispositivo
    public Dispositivo saveDispositivo(Dispositivo dispositivo) {
        return dispositivoRepository.save(dispositivo);
    }

    // Eliminar un dispositivo
    public void deleteDispositivo(Long id) {
        dispositivoRepository.deleteById(id);
    }
}

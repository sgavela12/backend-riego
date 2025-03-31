package com.sergio.backend_riego.controller;


import com.sergio.backend_riego.dto.PlantaDTO;
import com.sergio.backend_riego.model.Dispositivo;
import com.sergio.backend_riego.model.Planta;
import com.sergio.backend_riego.service.DispositivoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dispositivos")
@CrossOrigin(origins = "*") // Permite llamadas desde el frontend
public class DispositivoController {

    private final DispositivoService dispositivoService;

    public DispositivoController(DispositivoService dispositivoService) {
        this.dispositivoService = dispositivoService;
    }

    // Obtener todos los dispositivos
    @GetMapping
    public List<Dispositivo> obtenerDispositivos() {
        List<Dispositivo> dispositivos = dispositivoService.getAllDispositivos();
        System.out.println("Dispositivos desde la base de datos: " + dispositivos);
        return dispositivos;
    }

  

    // Obtener un dispositivo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Dispositivo> obtenerDispositivoPorId(@PathVariable Long id) {
        Optional<Dispositivo> dispositivo = dispositivoService.getDispositivoById(id);
        return dispositivo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Obtener un dispositivo por nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Dispositivo> obtenerDispositivoPorNombre(@PathVariable String nombre) {
        Optional<Dispositivo> dispositivo = dispositivoService.getDispositivoByNombre(nombre);
        return dispositivo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Agregar un nuevo dispositivo
    @PostMapping
    public ResponseEntity<Dispositivo> agregarDispositivo(@RequestBody Dispositivo dispositivo) {
        Dispositivo nuevoDispositivo = dispositivoService.saveDispositivo(dispositivo);
        return ResponseEntity.ok(nuevoDispositivo);
    }

    // Eliminar un dispositivo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDispositivo(@PathVariable Long id) {
        dispositivoService.deleteDispositivo(id);
        return ResponseEntity.noContent().build();
    }

    // Actualizar un dispositivo (opcional)
    @PutMapping("/{id}")
    public ResponseEntity<Dispositivo> actualizarDispositivo(@PathVariable Long id, @RequestBody Dispositivo dispositivo) {
        Optional<Dispositivo> dispositivoExistente = dispositivoService.getDispositivoById(id);
        if (dispositivoExistente.isPresent()) {
            dispositivo.setId(id);
            Dispositivo dispositivoActualizado = dispositivoService.saveDispositivo(dispositivo);
            return ResponseEntity.ok(dispositivoActualizado);
        }
        return ResponseEntity.notFound().build();
    }
}

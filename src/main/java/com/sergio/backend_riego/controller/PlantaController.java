package com.sergio.backend_riego.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.sergio.backend_riego.dto.PlantaDTO;
import com.sergio.backend_riego.model.Dispositivo;
import com.sergio.backend_riego.model.Planta;
import com.sergio.backend_riego.model.Riego;
import com.sergio.backend_riego.service.DispositivoService;
import com.sergio.backend_riego.service.PlantaService;
import com.sergio.backend_riego.service.RiegoService;

@RestController
@RequestMapping("/api/plantas")
@CrossOrigin(origins = "*") // Permite llamadas desde el frontend
public class PlantaController {

    private final PlantaService plantaService;
    private final RiegoService riegoService;
    private final DispositivoService dispositivoService;

    public PlantaController(PlantaService plantaService, RiegoService riegoService, DispositivoService dispositivoService) {
        this.plantaService = plantaService;
        this.riegoService = riegoService;
        this.dispositivoService = dispositivoService;
    }

    // Método para convertir una Planta a PlantaDTO
    private PlantaDTO convertirAPlantaDTO(Planta planta) {
        PlantaDTO dto = new PlantaDTO();
        dto.setId(planta.getId());
        dto.setNombre(planta.getNombre());
        dto.setTipo(planta.getTipo());
        dto.setUltimoRiego(planta.getUltimoRiego());
        dto.setNecesitaAgua(planta.isNecesitaAgua());

        // Verificar y asignar los IDs de la bomba y el sensor
        if (planta.getBomba() != null) {
            dto.setBombaId(planta.getBomba().getId());
        }
        if (planta.getSensor() != null) {
            dto.setSensorId(planta.getSensor().getId());
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

    // Nuevo endpoint para regar una planta
    @GetMapping("/{id}/regar")
    public ResponseEntity<String> regarPlanta(@PathVariable Long id) {
        Optional<Planta> plantaOptional = plantaService.getPlantaById(id);

        if (plantaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Planta planta = plantaOptional.get();
        if (planta.getBomba() == null) {
            return ResponseEntity.badRequest().body("La planta no tiene una bomba asociada.");
        }

        Long bombaId = planta.getBomba().getId();

        // Llamar al endpoint externo
        String url = "http://192.168.1.146/activar?bomba=" + bombaId;
        RestTemplate restTemplate = new RestTemplate();

        try {
            String respuesta = restTemplate.getForObject(url, String.class);

            // Crear un nuevo registro en la tabla Riego
            Riego nuevoRiego = new Riego();
            nuevoRiego.setEstado("exitoso"); // Estado del riego
            nuevoRiego.setFechaHora(LocalDateTime.now()); // Fecha y hora actual
            nuevoRiego.setPlanta(planta); // Asignar la planta
            nuevoRiego.setDispositivo(planta.getBomba()); // Asignar la bomba como dispositivo

            // Guardar el registro en la base de datos
            riegoService.registrarRiego(nuevoRiego);

            // Actualizar el campo ultimoRiego en la tabla Planta
            planta.setUltimoRiego(nuevoRiego.getFechaHora());
            plantaService.savePlanta(planta);

            return ResponseEntity.ok("Riego activado: " + respuesta + ". Registro creado en la tabla Riego.");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al activar el riego: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/registros")
    public ResponseEntity<List<Map<String, Object>>> obtenerRiegosPorPlanta(@PathVariable Long id) {
        // Buscar la planta por ID
        Optional<Planta> plantaOptional = plantaService.getPlantaById(id);
        if (plantaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Obtener los riegos asociados a la planta
        List<Riego> riegos = riegoService.getRiegosByPlantaId(id);

        // Transformar los riegos a un formato JSON con solo estado y fechaHora
        List<Map<String, Object>> riegosJson = riegos.stream()
            .map(riego -> {
                Map<String, Object> map = Map.of(
                    "estado", riego.getEstado(),
                    "fechaHora", riego.getFechaHora()
                );
                return (Map<String, Object>) map;
            })
            .collect(Collectors.toList());

        return ResponseEntity.ok(riegosJson);
    }

    @PostMapping("/riego")
    public ResponseEntity<String> registrarRiego(@RequestBody Riego riego) {
        // Validar dispositivo (bomba)
        Optional<Dispositivo> dispositivoOptional = dispositivoService.getDispositivoById(riego.getDispositivo().getId());
        if (dispositivoOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("El dispositivo con ID " + riego.getDispositivo().getId() + " no existe.");
        }

        // Buscar la planta asociada al dispositivo (bomba)
        Optional<Planta> plantaOptional = plantaService.getPlantaByBombaId(riego.getDispositivo().getId());
        if (plantaOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("No se encontró una planta asociada al dispositivo con ID " + riego.getDispositivo().getId());
        }

        // Asignar las entidades validadas al objeto Riego
        Planta planta = plantaOptional.get();
        Dispositivo dispositivo = dispositivoOptional.get();
        riego.setPlanta(planta);
        riego.setDispositivo(dispositivo);

        // Generar la fecha y hora dinámicamente
        riego.setFechaHora(LocalDateTime.now());

        // Guardar en la base de datos
        riegoService.registrarRiego(riego);

        // Actualizar el campo ultimoRiego en la tabla Planta
        planta.setUltimoRiego(riego.getFechaHora());
        plantaService.savePlanta(planta);

        return ResponseEntity.ok("Riego registrado exitosamente y campo 'ultimoRiego' actualizado.");
    }

    @GetMapping("/ambiente")
    public ResponseEntity<String> obtenerAmbiente() {
        String url = "http://192.168.1.146/ambiente";
        RestTemplate restTemplate = new RestTemplate();

        try {
            // Realizar la solicitud GET al endpoint externo
            String respuesta = restTemplate.getForObject(url, String.class);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            // Manejar errores en la solicitud
            return ResponseEntity.status(500).body("Error al consultar el ambiente: " + e.getMessage());
        }
    }

    @GetMapping("/humedad")
    public ResponseEntity<Object> obtenerHumedad() {
        String url = "http://192.168.1.146/humedad";
        RestTemplate restTemplate = new RestTemplate();

        try {
            // Realizar la solicitud GET al endpoint externo
            Map<String, Object> respuesta = restTemplate.getForObject(url, Map.class);

            // Extraer la lista de sensores
            List<Map<String, Object>> sensores = (List<Map<String, Object>>) respuesta.get("sensores");

            // Transformar los datos para incluir el ID de la planta asociada, renombrar planta_id a id y eliminar sensor_id
            List<Map<String, Object>> sensoresConPlanta = sensores.stream()
                .map(sensor -> {
                    Long sensorId = ((Number) sensor.get("sensor_id")).longValue();
                    Optional<Planta> plantaOptional = plantaService.getPlantaBySensorId(sensorId);

                    Map<String, Object> resultado = new LinkedHashMap<>(); // Usar LinkedHashMap para mantener el orden
                    plantaOptional.ifPresent(planta -> resultado.put("id", planta.getId())); // Agregar el campo id primero
                    resultado.put("humedad", sensor.get("humedad")); // Agregar el campo humedad

                    return resultado;
                })
                .collect(Collectors.toList());

            // Devolver la nueva estructura
            return ResponseEntity.ok(Map.of("sensores", sensoresConPlanta));
        } catch (Exception e) {
            // Manejar errores en la solicitud
            return ResponseEntity.status(500).body("Error al consultar la humedad: " + e.getMessage());
        }
    }
}

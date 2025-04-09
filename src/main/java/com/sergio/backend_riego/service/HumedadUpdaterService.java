package com.sergio.backend_riego.service;

import com.sergio.backend_riego.model.Planta;
import com.sergio.backend_riego.repository.PlantaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class HumedadUpdaterService {

    private final PlantaRepository plantaRepository;

    public HumedadUpdaterService(PlantaRepository plantaRepository) {
        this.plantaRepository = plantaRepository;
    }

    // @Scheduled(fixedRate = 40000) // Ejecutar cada 40 segundos (para pruebas)
    @Scheduled(cron = "0 0 */6 * * *") // Ejecutar cada 6 horas 
    public void actualizarHumedad() {
        System.out.println("Ejecutando tarea programada para actualizar la humedad...");
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://192.168.1.146/humedad"; // URL del endpoint

        try {
            Map<String, List<Map<String, Object>>> respuesta = restTemplate.getForObject(url, Map.class);
            System.out.println("Respuesta del endpoint: " + respuesta);

            if (respuesta != null && respuesta.containsKey("sensores")) {
                List<Map<String, Object>> sensores = respuesta.get("sensores");

                // Procesar los datos de los sensores
                for (Map<String, Object> sensor : sensores) {
                    Long sensorId = ((Number) sensor.get("id")).longValue();
                    Integer humedad = (Integer) sensor.get("humedad");

                    // Buscar la planta asociada al sensor (Dispositivo)
                    Planta planta = plantaRepository.findBySensorId(sensorId);
                    if (planta != null) {
                        // Actualizar la humedad de la planta
                        planta.setHumedad(humedad);
                        plantaRepository.save(planta); // Guardar los cambios en la base de datos
                        System.out.println("Humedad actualizada para la planta con ID: " + planta.getId());
                    } else {
                        System.out.println("No se encontró una planta asociada al sensor con ID: " + sensorId);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al obtener la humedad: " + e.getMessage());
        }
    }
}
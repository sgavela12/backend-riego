package com.sergio.backend_riego.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.sergio.backend_riego.model.Planta;
import com.sergio.backend_riego.service.PlantaService;

@RestController
@RequestMapping("/alexa")
public class AlexaController {

    @Autowired
    private PlantaService plantaService; // Servicio para buscar plantas en la base de datos

    @PostMapping("/consultar-humedad")
    public Map<String, Object> consultarHumedad() {
        // Llamar al endpoint local
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://192.168.1.150:8087/api/plantas/humedad";

        // Hacer la solicitud y parsear respuesta
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        List<Map<String, Object>> sensores = (List<Map<String, Object>>) response.getBody().get("sensores");

        // Armar mensaje para Alexa
        StringBuilder mensaje = new StringBuilder("Las humedades actuales de las plantas son: ");
        for (Map<String, Object> sensor : sensores) {
            Long plantaId = ((Number) sensor.get("id")).longValue(); // Obtener el ID de la planta
            Planta planta = plantaService.getPlantaById(plantaId).orElse(null); // Buscar la planta en la base de datos

            if (planta != null) {
                mensaje.append(planta.getNombre()) // Usar el nombre de la planta
                       .append(" con ")
                       .append(sensor.get("humedad"))
                       .append(" por ciento, ");
            } else {
                mensaje.append("Planta desconocida") // Si no se encuentra la planta
                       .append(" con ")
                       .append(sensor.get("humedad"))
                       .append(" por ciento, ");
            }
        }

        // Estructura de respuesta para Alexa
        Map<String, Object> alexaResponse = new HashMap<>();
        alexaResponse.put("version", "1.0");

        Map<String, Object> outputSpeech = new HashMap<>();
        outputSpeech.put("type", "PlainText");
        outputSpeech.put("text", mensaje.toString());

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("outputSpeech", outputSpeech);

        alexaResponse.put("response", responseBody);

        return alexaResponse;
    }
}

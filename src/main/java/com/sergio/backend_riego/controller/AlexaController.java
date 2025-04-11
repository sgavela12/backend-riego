package com.sergio.backend_riego.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/alexa")
public class AlexaController {

    @PostMapping("/consultar-humedad")
    public Map<String, Object> consultarHumedad() {
        // Llamar al endpoint local
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://192.168.1.150:8087/api/plantas/humedad";

        // Hacer la solicitud y parsear respuesta
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        List<Map<String, Object>> sensores = (List<Map<String, Object>>) response.getBody().get("sensores");

        // Armar mensaje para Alexa
        StringBuilder mensaje = new StringBuilder("Las humedades actuales son: ");
        for (Map<String, Object> sensor : sensores) {
            mensaje.append("Sensor ")
                   .append(sensor.get("id"))
                   .append(" con ")
                   .append(sensor.get("humedad"))
                   .append(" por ciento. ");
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

package com.sergio.backend_riego.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule; // Importa el módulo
import com.sergio.backend_riego.model.SensorData;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

public class RiegoWebSocketHandler extends TextWebSocketHandler {

    private static final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper(); // Para convertir objetos a JSON

    public RiegoWebSocketHandler() {
        // Registrar el módulo JavaTimeModule en el ObjectMapper
        objectMapper.registerModule(new JavaTimeModule());

        // Simular datos cada 5 segundos
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    // Simular varias lecturas de sensores
                    List<SensorData> lecturas = new ArrayList<>();

                    // Lectura 1
                    SensorData lectura1 = new SensorData();
                    lectura1.setValor(Math.round(Math.random() * 100.0) / 100.0);
                    lectura1.setFechaHora(LocalDateTime.now());
                    lectura1.setTipoParametro("Temperatura");
                    lectura1.setUnidad("°C");
                    // Aquí podrías asignar un dispositivo si lo necesitas
                    lecturas.add(lectura1);
                    System.out.println("PRUEBA = " + LocalDateTime.now());

                    // Lectura 2
                    SensorData lectura2 = new SensorData();
                    lectura2.setValor(Math.round(Math.random() * 100.0) / 100.0);
                    lectura2.setFechaHora(LocalDateTime.now());
                    lectura2.setTipoParametro("Humedad");
                    lectura2.setUnidad("%");
                    lecturas.add(lectura2);

                 
                    // Convertir la lista de lecturas a JSON
                    String jsonMessage = objectMapper.writeValueAsString(lecturas);

                    System.out.println("Enviando: " + jsonMessage);
                    broadcast(jsonMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 15000); // Enviar cada 5 segundos
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("Nueva conexión WebSocket: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Mensaje recibido: " + message.getPayload());
        broadcast(message.getPayload());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("Conexión cerrada: " + session.getId());
    }

    public static void broadcast(String message) throws IOException {
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(message));
            }
        }
    }
}
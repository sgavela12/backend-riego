# Sistema de Riego Automático con Monitoreo de Planta

Este es el backend de un sistema de riego automático para plantas que también permite el monitoreo de las condiciones ambientales (temperatura, humedad, humedad del suelo) y permite controlar la cámara de la planta, todo a través de una interfaz web.

## Tecnologías utilizadas

- **Spring Boot**: Framework principal para el backend.
- **WebSocket**: Para actualizar los datos en tiempo real en la interfaz web.
- **MySQL**: Base de datos para almacenar las lecturas de sensores y configuraciones de riego.
- **React**: Para el frontend (aunque este archivo es solo para el backend, el frontend también interactúa con este backend).
- **ESP32**: Usado para la lectura de sensores y control de dispositivos como la bomba de agua y la cámara.

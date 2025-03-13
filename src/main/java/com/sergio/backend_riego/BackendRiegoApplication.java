package com.sergio.backend_riego;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@EnableWebSocket
public class BackendRiegoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendRiegoApplication.class, args);
	}

}

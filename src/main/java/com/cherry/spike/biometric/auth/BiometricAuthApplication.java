package com.cherry.spike.biometric.auth;

import com.cherry.spike.biometric.auth.repository.CargoRepositorio;
import com.cherry.spike.biometric.auth.repository.ImpDigitalRepositorio;
import com.cherry.spike.biometric.auth.repository.UsuarioRepositorio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class BiometricAuthApplication {
	private static final Logger log = LoggerFactory.getLogger(BiometricAuthApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(BiometricAuthApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(UsuarioRepositorio usuarioRepository, CargoRepositorio cargoRepositorio, ImpDigitalRepositorio impDigitalRepositorio) {
		return (args) -> {
			log.info("-------------------------------");
			log.info("H2");
			log.info("Finalizado");
			log.info("-------------------------------");
		};
	}
}

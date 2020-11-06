package com.cherry.spike.biometric.auth;

import com.cherry.spike.biometric.auth.model.dtos.UsuarioDTO;
import com.cherry.spike.biometric.auth.model.entidade.Cargo;
import com.cherry.spike.biometric.auth.model.entidade.Nivel;
import com.cherry.spike.biometric.auth.repository.CargoRepositorio;
import com.cherry.spike.biometric.auth.repository.ImpDigitalRepositorio;
import com.cherry.spike.biometric.auth.repository.NivelRepositorio;
import com.cherry.spike.biometric.auth.service.UsuarioServico;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
public class BiometricAuthApplication {
	private static final Logger log = LoggerFactory.getLogger(BiometricAuthApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(BiometricAuthApplication.class, args);
	}
	private static final int MAX_REQUEST_SIZE_IN_MB = 150;

	@Bean
	public CommandLineRunner demo(UsuarioServico usuarioServico, CargoRepositorio cargoRepositorio, ImpDigitalRepositorio impDigitalRepositorio, NivelRepositorio nivelRepositorio) {
		return (args) -> {
			log.info("-------------------------------");
			log.info("H2");
			log.info("Criando Nivel Administrador");
			Nivel administrador = new Nivel();
			administrador.setDescricao("Administrador");
			nivelRepositorio.save(administrador);
			log.info("Criando Cargo Diretor");
			Cargo diretor = new Cargo();
			diretor.setDescricao("Diretor");
			diretor.setNivel(administrador);
			cargoRepositorio.save(diretor);
			log.info("Usuario");
			UsuarioDTO usuario = new UsuarioDTO(0, "Everton", "Ferreira", diretor.getId(), "everton42", "123");
			usuarioServico.salvar(usuario);
			log.info("Finalizado");
			log.info("-------------------------------");
		};
	}

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		final MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize(DataSize.ofMegabytes(MAX_REQUEST_SIZE_IN_MB));
		factory.setMaxRequestSize(DataSize.ofMegabytes(MAX_REQUEST_SIZE_IN_MB));
		return factory.createMultipartConfig();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:8080");
			}
		};
	}
}

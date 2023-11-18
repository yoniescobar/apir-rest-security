package com.company.intecap.apibooks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ApiBooksApplication implements CommandLineRunner { //CommandLinRuner: permite ejecutar codigo despues de que se inicie el proyecto

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(ApiBooksApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String password = "springboot";

		for (int i = 0; i < 3; i++) { //Genera 3 contraseñas encriptadas con BCryptPasswordEncoder
			String passwordBcrypt = passwordEncoder.encode(password);
			System.out.println(passwordBcrypt);
		}

	}
}

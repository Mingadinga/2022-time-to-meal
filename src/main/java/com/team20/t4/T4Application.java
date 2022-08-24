package com.team20.t4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class T4Application {

	public static void main(String[] args) {
		SpringApplication.run(T4Application.class, args);
	}

}

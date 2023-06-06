package com.dgd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DgdApplication {

	public static void main(String[] args) {
		SpringApplication.run(DgdApplication.class, args);
	}

}

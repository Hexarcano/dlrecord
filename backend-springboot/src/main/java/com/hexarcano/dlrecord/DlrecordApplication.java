package com.hexarcano.dlrecord;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DlrecordApplication {

	public static void main(String[] args) {
		SpringApplication.run(DlrecordApplication.class, args);
	}

}

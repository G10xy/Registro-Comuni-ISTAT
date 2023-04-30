package it.municipalitiesregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MunicipalitiesRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MunicipalitiesRegistryApplication.class, args);
	}

}

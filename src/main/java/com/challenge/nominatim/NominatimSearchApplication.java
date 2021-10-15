package com.challenge.nominatim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class NominatimSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(NominatimSearchApplication.class, args);
	}

}

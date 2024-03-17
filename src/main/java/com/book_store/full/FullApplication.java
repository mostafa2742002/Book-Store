package com.book_store.full;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

// wellcome
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableCaching
public class FullApplication {

	public static void main(String[] args) {
		SpringApplication.run(FullApplication.class, args);
	}

	// sudo systemctl start mongod
	// sudo systemctl status mongod

	// sudo systemctl enable docker
	// sudo systemctl start docker
	// sudo systemctl status docker

	// sudo systemctl status elasticsearch.service
	// sudo systemctl start elasticsearch.service
	// sudo systemctl stop elasticsearch.service

	// notes :
	// Rate Limiting : based on the user -> 429 too many requests
	// Load Shedding : based on the server status -> 503 server unavailable

}

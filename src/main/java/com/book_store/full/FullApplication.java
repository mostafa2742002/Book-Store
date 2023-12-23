package com.book_store.full;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// wellcome
@SpringBootApplication(exclude = SecurityAutoConfiguration.class) 
public class FullApplication {

	public static void main(String[] args) {
		SpringApplication.run(FullApplication.class, args);
	}
	// sudo systemctl start mongod
	// sudo systemctl status mongod
}

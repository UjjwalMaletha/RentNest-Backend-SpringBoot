package com.ujjwalmaletha.arbnbbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ujjwalmaletha.arbnbbackend"})
public class ArbnbbackendcloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArbnbbackendcloneApplication.class, args);
	}

}

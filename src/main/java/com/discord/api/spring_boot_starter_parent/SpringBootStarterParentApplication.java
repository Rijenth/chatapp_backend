package com.discord.api.spring_boot_starter_parent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringBootStarterParentApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootStarterParentApplication.class, args);
	}

}

package ru.kliuevia.springapp;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@RequiredArgsConstructor
public class SpringappApplication {

    private final ApplicationContext context;

    @Value("${user.enable}")
    private boolean flag;

	public static void main(String[] args) {
		SpringApplication.run(SpringappApplication.class, args);
	}
}

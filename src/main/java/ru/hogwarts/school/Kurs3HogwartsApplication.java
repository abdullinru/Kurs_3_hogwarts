package ru.hogwarts.school;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class Kurs3HogwartsApplication {

    public static void main(String[] args) {
        SpringApplication.run(Kurs3HogwartsApplication.class, args);
    }

}

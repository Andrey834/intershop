package ru.big.intershop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IntershopApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntershopApplication.class, args);
    }

}

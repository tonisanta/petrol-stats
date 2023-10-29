package com.modulith.petrolhttp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.modulith.petrolhttp","com.modulith.petrolstats"})
@SpringBootApplication
public class PetrolHttpApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetrolHttpApplication.class, args);
    }

}

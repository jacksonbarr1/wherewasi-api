package com.wherewasi.wherewasiapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class WherewasiApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WherewasiApiApplication.class, args);
    }

}

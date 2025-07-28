package com.wherewasi.wherewasi_api;

import com.wherewasi.wherewasiapi.WherewasiApiApplication;
import org.springframework.boot.SpringApplication;

public class TestWherewasiApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(WherewasiApiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}

package com.webapp.fakepeople;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.Arrays;

@SpringBootApplication
@EnableCaching
public class FakePeopleApplication {

	public static void main(String[] args) {
		SpringApplication.run(FakePeopleApplication.class, args);
	}
}

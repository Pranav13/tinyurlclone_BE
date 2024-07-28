package com.example.tinyurlclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TinyurlcloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(TinyurlcloneApplication.class, args);
	}

}

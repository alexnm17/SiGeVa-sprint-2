package edu.esi.uclm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class SiGeVaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiGeVaApplication.class, args);
	}

}

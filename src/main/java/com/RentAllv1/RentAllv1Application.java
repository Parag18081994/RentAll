package com.RentAllv1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class RentAllv1Application {

	public static void main(String[] args) {
		SpringApplication.run(RentAllv1Application.class, args);
	}

}

package com.eka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages= {"com.eka","com.ekaplus"})
public class EifMockServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EifMockServiceApplication.class, args);
	}
}

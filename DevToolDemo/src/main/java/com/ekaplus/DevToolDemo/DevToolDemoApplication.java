package com.ekaplus.DevToolDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(Configs.class)
public class DevToolDemoApplication implements CommandLineRunner {

	
	@Autowired
	Configs configs;
	
	public static void main(String[] args) {
		SpringApplication.run(DevToolDemoApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println("********"+configs.getName());
	}

}

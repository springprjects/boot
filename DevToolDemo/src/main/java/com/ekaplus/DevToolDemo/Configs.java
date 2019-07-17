package com.ekaplus.DevToolDemo;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix="mail")
@Getter
@Setter
public class Configs {
	
	private String name;

}

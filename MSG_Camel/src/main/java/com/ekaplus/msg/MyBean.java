package com.ekaplus.msg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyBean {

	@Value("${greeting}")
	private String say;

	public String saySomething() {
		return say;
	}

}

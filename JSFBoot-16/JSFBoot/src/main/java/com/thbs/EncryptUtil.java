package com.thbs;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptUtil {

	public static void main(String[] args) {
		BCryptPasswordEncoder c=new BCryptPasswordEncoder();
		
		System.out.println(c.encode("admin"));
	}
}

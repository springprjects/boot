package com.thbs.managedbeans;

import javax.annotation.ManagedBean;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.Getter;
import lombok.Setter;

@ManagedBean
@Getter
@Setter
public class HelloWorld {

	
	  private String firstName = "";
	  private String lastName = "";

	
	  public String showGreeting() {
	    Authentication authentication =
	        SecurityContextHolder.getContext().getAuthentication();

	    return "Hello " + authentication.getName() + "!";
	  }
	  
}

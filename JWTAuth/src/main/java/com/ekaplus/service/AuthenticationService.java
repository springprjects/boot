package com.ekaplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ekaplus.entity.AuthenticateRequest;
import com.ekaplus.util.JWTUtil;

@RestController
public class AuthenticationService {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@PostMapping(path="/authenticate")
	public ResponseEntity<String> authenticate(@RequestBody AuthenticateRequest authRequest) throws Exception{
		
		try {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
		}catch (BadCredentialsException e) {
			//throw new Exception("Incorrect Username and Password");
			return new ResponseEntity<String>("Incorrect Username / Password", HttpStatus.FORBIDDEN);
		}
		
		final UserDetails userDetails =myUserDetailsService.loadUserByUsername(authRequest.getUserName());
		
		final String token = jwtUtil.generateToken(userDetails);
		
		return new ResponseEntity<String>(token, HttpStatus.OK);
	}

	
	
	
}

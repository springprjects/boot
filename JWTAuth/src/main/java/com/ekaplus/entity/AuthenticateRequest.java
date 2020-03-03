package com.ekaplus.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AuthenticateRequest {

	private String userName;
	private String password;
	
}

package com.ekaplus.io;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int id;

	private String address;

	private String age;

	private String name;



}
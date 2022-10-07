package com.spring.reactive.excpetion;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StudentNotFoundExcpetion extends Exception{

	
	private static final long serialVersionUID = 1L;

	public StudentNotFoundExcpetion(String messsage) {
		// TODO Auto-generated constructor stub
		super(messsage);
	}
}

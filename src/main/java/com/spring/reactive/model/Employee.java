package com.spring.reactive.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.*;

@Table
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class Employee {
	@Id
	private long id;
	private String firstName;
	private String lastName;
	private int age;
	private String department;
}

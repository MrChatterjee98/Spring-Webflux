package com.spring.reactive.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.spring.reactive.service.EmployeeService;

@Configuration
public class Controller{

	@Autowired
	EmployeeService service;
	
	@Bean
	public RouterFunction<ServerResponse> getRouters(){
		return RouterFunctions.route()
				.GET("/employee",service::getAllEmployees)
				.GET("/employee/{id}",service::getEmployeeFromId)
				.build();
	}
	
	@Bean 
	RouterFunction<ServerResponse> postRouters(){
		return RouterFunctions.route()
				.POST("/employee",service::insertEmployee)
				.PUT("/employee/{id}", service::updateEmployee)
				.DELETE("employee/{id}",service::deleteEmployee)
				.build();
	}
	
	
	
}

package com.spring.reactive.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.spring.reactive.excpetion.StudentNotFoundExcpetion;
import com.spring.reactive.model.Employee;
import com.spring.reactive.repository.EmployeeRepository;
import com.spring.reactive.util.ResponseMessage;
import com.spring.reactive.util.UpdateObj;

import reactor.core.publisher.*;

@Service
public class EmployeeService {
	
	@Autowired
	EmployeeRepository repo;
	
	public Mono<ServerResponse> getAllEmployees(ServerRequest req){
		Flux<?> empList= repo.findAll().log();
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(empList,Employee.class);
	}
	
	public Mono<ServerResponse> insertEmployee(ServerRequest req){
		Mono<Employee> emp = req.bodyToMono(Employee.class);
		emp = emp.flatMap(i->{
			return repo.save(i);
		});
		return ServerResponse.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(emp,Employee.class);
	}
	
	public Mono<ServerResponse> getEmployeeFromId(ServerRequest req){
		long id = Long.valueOf(req.pathVariable("id"));
		Mono<Employee> emp =repo.findById(id);
		return emp
			.flatMap(x->ServerResponse.status(200).contentType(MediaType.APPLICATION_JSON).body(emp,Employee.class))
			.switchIfEmpty(Mono.error(new StudentNotFoundExcpetion("Employee with id: "+id+" is not present in db")));
		
	}
	
	@Transactional
	public Mono<ServerResponse> deleteEmployee(ServerRequest req){
		long id= Long.valueOf(req.pathVariable("id"));
		repo.deleteById(id).then();
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(new ResponseMessage("Employee with id: "+id+" has been removed")),ResponseMessage.class);}

	
	public Mono<ServerResponse> updateEmployee(ServerRequest req){
		long id = Long.valueOf(req.pathVariable("id"));
		Mono<UpdateObj> obj = req.bodyToMono(UpdateObj.class);
		Mono<Employee> e = repo.findById(id).switchIfEmpty(Mono.error(new StudentNotFoundExcpetion("student not found")));
		e = e.flatMap(emp->
			obj.flatMap(o->{
					if(o.getFieldName().equalsIgnoreCase("firstName"))
						emp.setFirstName(o.getNewValue());
					else if(o.getFieldName().equalsIgnoreCase("lastName"))
						emp.setLastName(o.getNewValue());
					else if(o.getFieldName().equalsIgnoreCase("age"))
						emp.setAge(Integer.valueOf(o.getNewValue()));
					else if(o.getFieldName().equalsIgnoreCase("department"))
						emp.setDepartment(o.getNewValue());
					return Mono.just(emp);
			})
		);
		e = e.flatMap(emp->repo.save(emp));
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(e,Employee.class);
		
	}
}

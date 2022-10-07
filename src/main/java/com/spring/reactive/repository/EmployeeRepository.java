package com.spring.reactive.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.reactive.model.Employee;

import reactor.core.publisher.Mono;
@Repository
@Transactional
public interface EmployeeRepository extends ReactiveCrudRepository<Employee, Long>{
	
	@Query(value = "DELETE FROM EMPLOYEE WHERE ID= $1")
	public Mono<Integer> deleteEmployee(long id);

}

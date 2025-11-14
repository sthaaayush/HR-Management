package com.aayush.hrManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aayush.hrManagement.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	List<Employee> searchByFirstNameContainingIgnoreCase(String firstName);
}

package com.aayush.hrManagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aayush.hrManagement.entity.Leave;

public interface LeaveRepository extends JpaRepository<Leave, Long>{
	void deleteByEmployeeId(long employeeId);
	Optional<Leave> findByEmployeeId(long employeeId);
}

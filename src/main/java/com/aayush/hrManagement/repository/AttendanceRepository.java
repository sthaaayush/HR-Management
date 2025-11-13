package com.aayush.hrManagement.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aayush.hrManagement.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>{
	Optional<Attendance> findByEmployeeIdAndDate(long employeeId, LocalDate date);
	List<Attendance> findByDate(LocalDate date);
	void deleteByEmployeeId(long employeeId);
}

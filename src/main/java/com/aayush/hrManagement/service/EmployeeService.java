package com.aayush.hrManagement.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aayush.hrManagement.entity.Employee;
import com.aayush.hrManagement.repository.AttendanceRepository;
import com.aayush.hrManagement.repository.EmployeeRepository;
import com.aayush.hrManagement.repository.LeaveRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepo;

	@Autowired
	private LeaveRepository leaveRepo;

	@Autowired
	private AttendanceRepository attendanceRepo;

	public List<Employee> getAllEmployee() {
		return employeeRepo.findAll();
	}

	public Optional<Employee> getEmployeeById(long searchId) {
		return employeeRepo.findById(searchId);
	}

	public Employee getEmployeeByIdNew(long searchId) {
		return employeeRepo.findById(searchId).get();
	}

	public Employee saveEmployee(Employee employee) {
		if (employee.getDateOfJoining() == null) {
			employee.setDateOfJoining(LocalDate.now());
		}

		employee.setEmploymentStatus(true);

		return employeeRepo.save(employee);
	}

	public List<Employee> saveAllEmployee(List<Employee> employee) {
		for (Employee emp : employee) {
			if (emp.getDateOfJoining() == null) {
				emp.setDateOfJoining(LocalDate.now());
			}

			emp.setEmploymentStatus(true);
			;
		}
		return employeeRepo.saveAll(employee);
	}

	public Employee updateEmployeeById(Long Id, Employee employee) {
		Optional<Employee> dbData = employeeRepo.findById(Id);
		if (dbData.isPresent()) {
			Employee collect = dbData.get();
			if (employee.getFirstName() != null)
				collect.setFirstName(employee.getFirstName());
			if (employee.getLastName() != null)
				collect.setLastName(employee.getLastName());
			if (employee.getEmail() != null)
				collect.setEmail(employee.getEmail());
			if (employee.getPhoneNumber() != null)
				collect.setPhoneNumber(employee.getPhoneNumber());
			if (employee.getAddress() != null)
				collect.setAddress(employee.getAddress());
			if (employee.getDepartment() != null)
				collect.setDepartment(employee.getDepartment());
			if (employee.getJobTitle() != null)
				collect.setJobTitle(employee.getJobTitle());
			if (employee.getSalary() != 0) {
				if (employee.getSalary() < 0) {
					throw new IllegalArgumentException("Salary cannot be negative");
				}
				collect.setSalary(employee.getSalary());
			}
			collect.setEmploymentStatus(employee.isEmploymentStatus());
			return employeeRepo.save(collect);
		}
		return null;
	}

	@Transactional
	public boolean deleteEmployeeById(long searchId) {
		employeeRepo.deleteById(searchId);
		attendanceRepo.deleteByEmployeeId(searchId);
		leaveRepo.deleteByEmployeeId(searchId);
		return true;
	}

}

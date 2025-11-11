package com.aayush.hrManagement.service;


import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import com.aayush.hrManagement.entity.Employee;
import com.aayush.hrManagement.repository.EmployeeRepository;

import org.mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

	@InjectMocks
	EmployeeService underTest;
	
	@Mock
	EmployeeRepository employeeRepo;
	
	Employee emp1 = new Employee();
	
	@BeforeEach
	void employeeAdded() {
		emp1.setEmployeeId(1);
		emp1.setFirstName("Aayush");
		emp1.setLastName("Shrestha");
		emp1.setEmail("aayush.shrestha@hrms.com");
		emp1.setPhoneNumber("9812345678");
		emp1.setDepartment("Human Resources");
		emp1.setJobTitle("HR Manager");
		emp1.setSalary(85000.00);
		emp1.setAddress("Kathmandu, Nepal");
	}
	
	
	@Test
	@DisplayName("Save Employee")
	void testSaveEmployee() {
		when(employeeRepo.save(emp1)).thenReturn(emp1);
		Employee result = underTest.saveEmployee(emp1);
		assertEquals(emp1, result);
		verify(employeeRepo).save(emp1);
	}

	
	@ParameterizedTest
	@DisplayName("Update Employee By Id with different firstName")
	@ValueSource(strings = {
		"Aati",
		"Heer",
		"Sam",
		"Keem"
	})
	void paramTestUpdateEmployeeById(String fName) {
		Employee updatedEmployee = new Employee();
		updatedEmployee.setFirstName(fName);
		
		when(employeeRepo.findById(1L)).thenReturn(Optional.of(emp1));
		when(employeeRepo.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));
		
		Employee result = underTest.updateEmployeeById(1L, updatedEmployee);
		
		assertNotNull(result);
		assertEquals(fName, result.getFirstName());
		assertEquals(emp1.getLastName(), result.getLastName());
		
		verify(employeeRepo).findById(1L);
		verify(employeeRepo).save(any(Employee.class));
	}
}
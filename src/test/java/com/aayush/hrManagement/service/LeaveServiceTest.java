package com.aayush.hrManagement.service;


import static org.mockito.ArgumentMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.aayush.hrManagement.entity.Employee;
import com.aayush.hrManagement.entity.Leave;
import com.aayush.hrManagement.repository.LeaveRepository;

import org.mockito.*;

@ExtendWith(MockitoExtension.class)
class LeaveServiceTest {

	@InjectMocks
	LeaveService underTest;

	@Mock
	LeaveRepository leaveRepo;
	
	@Mock
	EmployeeService employeeServ;
	
	static Leave leave = new Leave();
	static Leave leave2 = new Leave();
	static Employee emp1 = new Employee();

	@BeforeAll
	public static void beforeAll() {
		leave.setEmployeeId(1L);
		leave.setStartDate(LocalDate.of(2025, 11, 10));
		leave.setEndDate(LocalDate.of(2025, 11, 15));
		leave.setLeaveType(Leave.LeaveType.ANNUAL);
		leave.setReason("Family vacation");
		leave.setDecisionDate(null);
		
		leave2.setEmployeeId(1L);
		leave2.setStartDate(LocalDate.of(2025, 11, 16));
		leave2.setEndDate(LocalDate.of(2025, 11, 18));
		leave2.setLeaveType(Leave.LeaveType.ANNUAL);
		leave2.setReason("Family vacation");
		leave2.setDecisionDate(null);
		
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
	void testAddLeave() throws Exception {
		when(employeeServ.getEmployeeById(1L)).thenReturn(Optional.of(emp1));
		when(employeeServ.saveEmployee(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));
		when(leaveRepo.save(any(Leave.class))).thenAnswer(invocation -> invocation.getArgument(0));
		
		Leave result = underTest.addLeave(leave);
		
		assertEquals(result.getLeaveDays(), 6);
		assertEquals(result.getAppliedDate(), LocalDate.now());
		assertEquals(result.getStatus(), Leave.Status.PENDING);
		
		verify(employeeServ).getEmployeeById(1L);
		verify(employeeServ).saveEmployee(any(Employee.class));
		verify(leaveRepo).save(any(Leave.class));
	}

}
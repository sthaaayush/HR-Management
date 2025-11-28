package com.aayush.hrManagement.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.aayush.hrManagement.entity.Attendance;
import com.aayush.hrManagement.entity.Employee;
import com.aayush.hrManagement.entity.Leave;
import com.aayush.hrManagement.repository.AttendanceRepository;
import com.aayush.hrManagement.repository.EmployeeRepository;
import com.aayush.hrManagement.repository.LeaveRepository;

@ExtendWith(MockitoExtension.class)
public class HrManagementintegratedTest {
	@Mock
	AttendanceRepository attendanceRepo;

	@Mock 
	EmployeeRepository employeeRepo;
	
	@Mock
	LeaveRepository leaveRepo;

	@Mock
	LeaveService leaveServ;

	@Mock
	AttendanceService attendanceServ;

	@Mock
	EmployeeService employeeServ;
	
	@InjectMocks
	EmployeeService employeeServUnderTest;

	@InjectMocks
	AttendanceService attendanceServUnderTest;

	@InjectMocks
	LeaveService leaveServUnderTest;

	static Employee emp1 = new Employee();
	static Attendance att1 = new Attendance();
	static Leave lev1 = new Leave();

	@BeforeAll
	public static void intgTest() {
		emp1.setEmployeeId(1);
		emp1.setFirstName("Aayush");
		emp1.setLastName("Shrestha");
		emp1.setEmail("aayush.shrestha@hrms.com");
		emp1.setPhoneNumber("9812345678");
		emp1.setDepartment("Human Resources");
		emp1.setJobTitle("HR Manager");
		emp1.setAddress("Kathmandu, Nepal");

		att1.setAttendanceId(1);
		att1.setEmployeeId(1);
		att1.setDate(LocalDate.now());
		att1.setCheckInTime(LocalTime.of(9, 0));
		att1.setStatus(Attendance.Status.PRESENT);

		lev1.setEmployeeId(1L);
		lev1.setStartDate(LocalDate.of(2025, 11, 20));
		lev1.setEndDate(LocalDate.of(2025, 11, 25));
		lev1.setLeaveType(Leave.LeaveType.ANNUAL);
		lev1.setReason("Family vacation");
		lev1.setDecisionDate(null);
		lev1.setLeaveId(1l);
	}

	@Test
	@DisplayName("Employee Save Test")
	void testHrManagementIntegratedTest() {
		when(employeeRepo.save(emp1)).thenReturn(emp1);
//		when(attendanceRepo.save(any(Attendance.class))).thenReturn(att1);
//		when(employeeServ.getEmployeeById(1)).thenReturn(Optional.of(emp1));
//		when(leaveRepo.save(lev1)).thenReturn(lev1);
		
		Employee empResult = employeeServUnderTest.saveEmployee(emp1);
//		Attendance attdResult = attendanceServUnderTest.saveCheckInEntry(1);
//		Leave levResult = leaveServUnderTest.addLeave(lev1);
		
		assertEquals(empResult.getEmail(), emp1.getEmail());
//		assertEquals(attdResult, att1);
//		assertEquals(levResult.getLeaveDays(), lev1.calcualteLeaveDays(lev1.getStartDate(), lev1.getEndDate()));
		
		verify(employeeRepo).save(emp1);
//		verify(attendanceRepo).save(any(Attendance.class));
//		verify(employeeServ).getEmployeeById(1);
//		verify(leaveRepo).save(lev1);
	}
	
//	@Test
//	@DisplayName("Attendance Save Test")
//	void testHrIntegratedAttendance() {
//		when(attendanceRepo.save(any(Attendance.class))).thenReturn(att1);
//		when(employeeServ.getEmployeeById(1)).thenReturn(Optional.of(emp1));
//		
//		Attendance attdResult = new Attendance();
//		attdResult = attendanceServUnderTest.saveCheckInEntry(1);
//		
//		System.out.println(attdResult);
//
//		assertEquals(attdResult, att1);
//		
//		verify(attendanceRepo).save(any(Attendance.class));
//		verify(employeeServ).getEmployeeById(1);
//	}
	
	@Test
	@DisplayName("Leave Save Test")
	void testHrIntegratedLeave() {
		when(employeeServ.getEmployeeById(1L)).thenReturn(Optional.of(emp1));
		when(employeeServ.saveEmployee(emp1)).thenReturn(emp1);
		when(leaveRepo.save(lev1)).thenReturn(lev1);
		
		Leave levResult = leaveServUnderTest.addLeave(lev1);

		System.out.println(levResult);
		assertEquals(levResult.getLeaveDays(), lev1.calculateLeaveDays(lev1.getStartDate(), lev1.getEndDate()));
		
		verify(employeeServ).getEmployeeById(1L);
		verify(leaveRepo).save(lev1);
	}
}

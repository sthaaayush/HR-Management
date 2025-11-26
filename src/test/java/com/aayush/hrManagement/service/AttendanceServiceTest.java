package com.aayush.hrManagement.service;

import static org.mockito.ArgumentMatchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.aayush.hrManagement.entity.Attendance;
import com.aayush.hrManagement.entity.Employee;
import com.aayush.hrManagement.repository.AttendanceRepository;

@ExtendWith(MockitoExtension.class)
class AttendanceServiceTest {

	@Mock
	AttendanceRepository attendanceRepo;
	
	@Mock
	EmployeeService employeeServ = new EmployeeService();

	@InjectMocks
	AttendanceService underTest;

	static Attendance at1 = new Attendance();
	static Attendance at2 = new Attendance();
	static Employee emp1 = new Employee(); 
	static Employee emp2 = new Employee();
	static LocalTime currentTime = LocalTime.now();
	

	static List<Attendance> attdList = new ArrayList<>();
	static List<Employee> empList = new ArrayList<>();

	@BeforeAll
	public static void entitySet() {
		emp1.setEmployeeId(1);
		emp1.setFirstName("Aayush");
		emp1.setLastName("Shrestha");
		emp1.setEmail("aayush.shrestha@hrms.com");
		emp1.setPhoneNumber("9812345678");
		emp1.setDepartment("Human Resources");
		emp1.setJobTitle("HR Manager");
		emp1.setSalary(85000.00);
		emp1.setAddress("Kathmandu, Nepal");
		
		emp2.setEmployeeId(3);
		emp2.setFirstName("Anup");
		emp2.setLastName("Shrestha");
		emp2.setEmail("anup.shrestha@hrms.com");
		emp2.setPhoneNumber("9812345678");
		emp2.setDepartment("Human Resources");
		emp2.setJobTitle("HR Intern");
		emp2.setSalary(15000.00);
		emp2.setAddress("Pokhara, Nepal");
		
		empList.add(emp1);
		empList.add(emp2);
		
		at1.setAttendanceId(0);
		at1.setEmployeeId(1);
		at1.setDate(LocalDate.now());
		at1.setCheckInTime(LocalTime.now());
		at1.setStatus(Attendance.Status.LATE);
		
		at2.setAttendanceId(1);
		at2.setEmployeeId(2);
		at2.setDate(LocalDate.now());
		at2.setCheckInTime(LocalTime.of(12, 0));
		at2.setStatus(Attendance.Status.LATE);
		
		attdList.add(at1);
		attdList.add(at2);
	}

//	@Test
//	void testSaveCheckInEntry() throws Exception {
//		when(attendanceRepo.save(any(Attendance.class))).thenAnswer(invocation -> invocation.getArgument(0));
//		when(employeeServ.getEmployeeById(1L)).thenReturn(Optional.of(emp1));
//		Attendance atd = underTest.saveCheckInEntry(1L);
//		assertEquals(atd.getDate(), at1.getDate());
//		verify(attendanceRepo).save(any(Attendance.class));
//	}
//
//	@Test
//	void testSaveCheckOutEntry() throws Exception {
//		when(attendanceRepo.save(any(Attendance.class))).thenAnswer(invocation -> invocation.getArgument(0));
//		when(attendanceRepo.findByEmployeeIdAndDate(1L, LocalDate.now())).thenReturn(Optional.of(at2));
//		Attendance atd = underTest.saveCheckOutEntry(1L);
//		assertEquals(atd.getStatus(), Attendance.Status.ABSENT);
//		assertEquals(atd, at2);
//		verify(attendanceRepo).save(any(Attendance.class));
//		verify(attendanceRepo).findByEmployeeIdAndDate(1L, LocalDate.now());
//	}
	
	@Test
	void testEndWorkingShiftCall() throws Exception{
		when(attendanceRepo.findByDate(LocalDate.now())).thenReturn(attdList);
		when(employeeServ.getAllEmployee()).thenReturn(empList);
		when(attendanceRepo.saveAll(anyIterable())).thenAnswer(invocation -> invocation.getArgument(0));	
		
		List<Attendance> atd = underTest.endWorkingShiftCall();
		assertEquals(atd.get(0).getStatus(),Attendance.Status.HALF_DAY);
		assertEquals(atd.get(1).getStatus(), Attendance.Status.HALF_DAY);
		assertEquals(atd.get(2).getStatus(), Attendance.Status.ABSENT);
		
		verify(attendanceRepo).findByDate(LocalDate.now());
		verify(employeeServ).getAllEmployee();
		verify(attendanceRepo).saveAll(anyIterable());
	}

}
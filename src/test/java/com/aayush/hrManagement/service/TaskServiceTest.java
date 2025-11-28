package com.aayush.hrManagement.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.aayush.hrManagement.entity.Employee;
import com.aayush.hrManagement.entity.Task;
import com.aayush.hrManagement.repository.EmployeeRepository;
import com.aayush.hrManagement.repository.TaskRepository;

import org.mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

	@InjectMocks
	TaskService underTest;

	@Mock
	EmployeeRepository employeeRepo;

	@Mock
	TaskRepository taskRepo;

	static Task task = new Task();
	static Employee emp1 = new Employee();
	static Employee emp2 = new Employee();
	static List<Employee> empList = new ArrayList<>();
	
	@BeforeAll
	public static void makeTask() {
		task.setTaskId(1L);
		task.setTaskName("Prepare Monthly Attendance Report");
		task.setDescription("Compile attendance data for all departments for the month of November.");
		task.setAssignedToIds("3,7");
		task.setAssignedBy(1L);
		task.setStartDate(LocalDate.of(2025, 11, 10));
		task.setEndDate(LocalDate.of(2025, 11, 12));
		task.setPriority(Task.Priority.HIGH);
		
		emp1.setEmployeeId(1);
		emp1.setFirstName("Aayush");
		emp1.setLastName("Shrestha");
		emp1.setEmail("aayush.shrestha@hrms.com");
		emp1.setPhoneNumber("9812345678");
		emp1.setDepartment("Human Resources");
		emp1.setJobTitle("HR Manager");
		emp1.setAddress("Kathmandu, Nepal");
		
		emp2.setEmployeeId(2);
		emp2.setFirstName("Anup");
		emp2.setLastName("Shrestha");
		emp2.setEmail("anup.shrestha@hrms.com");
		emp2.setPhoneNumber("9812345678");
		emp2.setDepartment("Human Resources");
		emp2.setJobTitle("HR Intern");
		emp2.setAddress("Pokhara, Nepal");
		
		empList.add(emp1);
		empList.add(emp2);
	}

	@Test
	void testAddTask() throws Exception {
		when(employeeRepo.findAllById(anyList())).thenReturn(empList);
		when(employeeRepo.findById(1L)).thenReturn(Optional.of(emp1));
		when(taskRepo.save(task)).thenReturn(task);
		
		Task result = underTest.addTask(task);
		
		assertNotNull(result);
		assertEquals(result.getTaskName(), task.getTaskName());
		assertEquals(result.getStatus(), Task.Status.PENDING);
		
		verify(employeeRepo).findAllById(anyList());
		verify(employeeRepo).findById(1L);
		verify(taskRepo).save(task);
	}

}
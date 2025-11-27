package com.aayush.hrManagement.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aayush.hrManagement.entity.Task;
import com.aayush.hrManagement.service.EmployeeService;
import com.aayush.hrManagement.service.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {
	@Autowired
	private TaskService taskServ;

	@Autowired
	private EmployeeService employeeServ;

	@GetMapping("/viewTask")
	public ResponseEntity<?> viewTask() {
		List<Task> taskList = taskServ.showAllTask();
		if (taskList.isEmpty()) {
			return new ResponseEntity<String>("No Task Available", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Task>>(taskList, HttpStatus.OK);
	}

	@GetMapping("/empViewTask")
	public ResponseEntity<?> viewEmployeeTask() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		Long employeeId = employeeServ.getEmployeeByEmail(email).get().getEmployeeId();

		List<Task> taskList = taskServ.showAllTask();
		if (taskList.isEmpty()) {
			return new ResponseEntity<String>("No Task Available", HttpStatus.NO_CONTENT);
		}

		List<Task> employeeTaskList = new ArrayList<>();
		for (Task task : taskList) {
			List<Long> assignedToIds = Arrays.stream(task.getAssignedToIds().split(",")).map(Long::parseLong).toList();
			if (assignedToIds.contains(employeeId)) {
				employeeTaskList.add(task);
			}
		}
		return new ResponseEntity<List<Task>>(employeeTaskList, HttpStatus.OK);
	}

	@PostMapping("/addTask")
	public ResponseEntity<?> addTask(@RequestBody Task task) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		Long employeeId = employeeServ.getEmployeeByEmail(email).get().getEmployeeId();
		task.setAssignedBy(employeeId);
		try {
			Task taskDetsils = taskServ.addTask(task);
			return new ResponseEntity<Task>(taskDetsils, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/updateTask/{taskId}")
	public ResponseEntity<?> updateTask(@RequestBody Task task, @PathVariable long taskId) {
		try {
			task.setTaskId(taskId);
			Task updatedTask = taskServ.updatedTask(task);
			return new ResponseEntity<Task>(updatedTask, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/deleteTask/{deleteId}")
	public ResponseEntity<?> deleteTask(@PathVariable long deleteId){
		try {
			taskServ.deleteById(deleteId);
			return new ResponseEntity<String>("Task Deleted!", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}

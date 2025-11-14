package com.aayush.hrManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aayush.hrManagement.entity.Task;
import com.aayush.hrManagement.service.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {
	@Autowired
	private TaskService taskServ;

	@GetMapping("/viewTask")
	public ResponseEntity<?> viewTask() {
		List<Task> taskList = taskServ.showAllTask();
		if (taskList.isEmpty()) {
			return new ResponseEntity<String>("No Task Available", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Task>>(taskList, HttpStatus.OK);
	}

	@PostMapping("/addTask")
	public ResponseEntity<?> addTask(@RequestBody Task task) {
		try {
			Task taskDetsils = taskServ.addTask(task);
			return new ResponseEntity<Task>(taskDetsils, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

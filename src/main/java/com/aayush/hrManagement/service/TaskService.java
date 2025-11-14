package com.aayush.hrManagement.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aayush.hrManagement.entity.Employee;
import com.aayush.hrManagement.entity.Task;
import com.aayush.hrManagement.repository.EmployeeRepository;
import com.aayush.hrManagement.repository.TaskRepository;

@Service
public class TaskService {
	@Autowired
	private TaskRepository taskRepo;

	@Autowired
	private EmployeeRepository employeeRepo;

	public List<Task> showAllTask() {
		return taskRepo.findAll();
	}

	public Task addTask(Task task) {
		List<Long> assignedToIds = Arrays.stream(task.getAssignedToIds().split(",")).map(Long::parseLong).toList();
		List<Employee> assignedToList = employeeRepo.findAllById(assignedToIds);
		List<Long> foundAssignedToIds = assignedToList.stream().map(Employee::getEmployeeId).toList();
		if (foundAssignedToIds.size() != assignedToIds.size())
			throw new RuntimeException("One or more assigned to employee IDs are invalid!");

		Optional<Employee> assignedByEmployee = employeeRepo.findById(task.getAssignedBy());
		if (assignedByEmployee.isEmpty())
			throw new RuntimeException("One or more assigned by employee IDs are invalid!");

		if (task.getStatus() == null)
			task.setStatus(Task.Status.PENDING);
		return taskRepo.save(task);
	}

	public List<Task> bulkAddTask(List<Task> bulkTask) {
		for (Task iteTask : bulkTask) {
			if (iteTask.getStatus() == null)
				iteTask.setStatus(Task.Status.PENDING);
		}
		return taskRepo.saveAll(bulkTask);
	}
	
	
}

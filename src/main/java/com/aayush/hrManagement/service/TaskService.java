package com.aayush.hrManagement.service;

import java.time.LocalDate;
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
		LocalDate currentDate = LocalDate.now();
		List<Long> assignedToIds = Arrays.stream(task.getAssignedToIds().split(",")).map(Long::parseLong).toList();
		List<Employee> assignedToList = employeeRepo.findAllById(assignedToIds);
		List<Long> foundAssignedToIds = assignedToList.stream().map(Employee::getEmployeeId).toList();
		if (foundAssignedToIds.size() != assignedToIds.size())
			throw new RuntimeException("One or more assigned to employee IDs are invalid!");

		Optional<Employee> assignedByEmployee = employeeRepo.findById(task.getAssignedBy());
		if (assignedByEmployee.isEmpty())
			throw new RuntimeException("One or more assigned by employee IDs are invalid!");

		if (currentDate.isAfter(task.getStartDate()) || task.getStartDate().isAfter(task.getEndDate()))
			throw new RuntimeException("Invalid assigned date!");

		if (task.getStatus() == null)
			task.setStatus(Task.Status.PENDING);

		return taskRepo.save(task);
	}

	public Task updatedTask(Task task) {
		Task collect = taskRepo.findById(task.getTaskId()).get();
		if (collect.getStatus() != Task.Status.COMPLETED || collect.getStatus() != Task.Status.CANCELLED) {
			if (collect != null) {
				if (task.getAssignedBy() != null)
					collect.setAssignedBy(task.getAssignedBy());
				if (task.getAssignedToIds() != null)
					collect.setAssignedToIds(task.getAssignedToIds());
				if (task.getDescription() != null)
					collect.setDescription(task.getDescription());
				if (task.getStartDate() != null)
					collect.setStartDate(task.getStartDate());
				if (task.getEndDate() != null)
					collect.setEndDate(task.getEndDate());
				if (task.getTaskName() != null)
					collect.setTaskName(task.getTaskName());
				if (task.getPriority() != null)
					collect.setPriority(task.getPriority());
				if (task.getStatus() != null)
					collect.setStatus(task.getStatus());

				LocalDate currentDate = LocalDate.now();
				List<Long> assignedToIds = Arrays.stream(collect.getAssignedToIds().split(",")).map(Long::parseLong)
						.toList();
				List<Employee> assignedToList = employeeRepo.findAllById(assignedToIds);
				List<Long> foundAssignedToIds = assignedToList.stream().map(Employee::getEmployeeId).toList();
				if (foundAssignedToIds.size() != assignedToIds.size())
					throw new RuntimeException("One or more assigned to employee IDs are invalid!");

				Optional<Employee> assignedByEmployee = employeeRepo.findById(collect.getAssignedBy());
				if (assignedByEmployee.isEmpty())
					throw new RuntimeException("One or more assigned by employee IDs are invalid!");

				if (currentDate.isAfter(collect.getStartDate()) || collect.getStartDate().isAfter(collect.getEndDate()))
					throw new RuntimeException("Invalid assigned date!");

				return taskRepo.save(collect);
			}
		}
		return null;
	}
	
	public void deleteById(long id) {
		taskRepo.deleteById(id);
	}
}

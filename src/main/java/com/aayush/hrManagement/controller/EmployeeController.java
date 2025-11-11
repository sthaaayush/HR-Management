package com.aayush.hrManagement.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aayush.hrManagement.entity.Employee;
import com.aayush.hrManagement.service.EmployeeService;

@RestController
@Controller
@RequestMapping("/employees")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeServ;

	@GetMapping("/showAll")
	public List<Employee> findAllEmployee() {
		return employeeServ.getAllEmployee();
	}

	@GetMapping("/show/{searchId}")
	public ResponseEntity<Employee> findEmployeeById(@PathVariable long searchId) {
		Optional<Employee> collect = employeeServ.getEmployeeById(searchId);
		if (collect.isPresent()) {
			return new ResponseEntity<Employee>(collect.get(), HttpStatus.FOUND);
		}
		return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/add")
	public Employee addEmployee(@RequestBody Employee employee) {
		return employeeServ.saveEmployee(employee);
	}

	@PostMapping("/bulkAdd")
	public List<Employee> addBulkEmployee(@RequestBody List<Employee> employee) {
		return employeeServ.saveAllEmployee(employee);
	}

	@PutMapping("/update/{updateId}")
	public ResponseEntity<Employee> updateById(@PathVariable long updateId, @RequestBody Employee employee) {
		Employee result = employeeServ.updateEmployeeById(updateId, employee);
		if (result != null) {
			return new ResponseEntity<Employee>(result, HttpStatus.OK);
		}
		return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/id/{searchId}")
	public void removeEmployeeById(@PathVariable long searchId) {
		employeeServ.deleteEmployeeById(searchId);
	}

	
}

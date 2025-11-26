package com.aayush.hrManagement.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	public ResponseEntity<?> findAllEmployee() {
		List<Employee> employeeList = employeeServ.getAllEmployee();
		if (employeeList.isEmpty()) {
			return new ResponseEntity<String>("No employee found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Employee>>(employeeList, HttpStatus.OK);
	}

	@GetMapping("/show/{searchId}")
	public ResponseEntity<?> findEmployeeById(@PathVariable long searchId) {
		Optional<Employee> collect = employeeServ.getEmployeeById(searchId);
		if (collect.isPresent()) { 
			return new ResponseEntity<Employee>(collect.get(), HttpStatus.FOUND);
		}
		return new ResponseEntity<String>("No Employee with employeeId: " + searchId, HttpStatus.NOT_FOUND);
	}

	@GetMapping("/show")
	public ResponseEntity<?> findEmployee() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		Optional<Employee> collect = employeeServ.getEmployeeByEmail(email);
		if (collect.isPresent()) {
			return new ResponseEntity<Employee>(collect.get(), HttpStatus.FOUND);
		}
		return new ResponseEntity<String>("No Employee with employee email: " + email, HttpStatus.NOT_FOUND);
	}

	@PostMapping("/add")
	public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
		try {
			Employee savedEmp = employeeServ.saveEmployee(employee);
			return new ResponseEntity<Employee>(savedEmp, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/bulkAdd")
	public ResponseEntity<?> addBulkEmployee(@RequestBody List<Employee> employee) {
		try {
			List<Employee> savedEmployeeList = employeeServ.saveAllEmployee(employee);
			return new ResponseEntity<List<Employee>>(savedEmployeeList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
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

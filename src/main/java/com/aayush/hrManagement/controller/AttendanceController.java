package com.aayush.hrManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aayush.hrManagement.entity.Attendance;
import com.aayush.hrManagement.service.AttendanceService;
import com.aayush.hrManagement.service.EmployeeService;

@RestController
@RequestMapping("/entry")
public class AttendanceController {
	@Autowired
	private AttendanceService attendanceServ;

	@Autowired
	private EmployeeService employeeServ;

	@GetMapping("/showAll")
	public List<Attendance> showAll() {
		return attendanceServ.getAllEntry();
	}

	@GetMapping("/checkIn/{empId}")
	public ResponseEntity<String> checkIn(@PathVariable long empId) {
		Attendance entryResult = attendanceServ.saveCheckInEntry(empId);
		if (entryResult != null) {
			String empName = employeeServ.getEmployeeById(empId).get().getFirstName();
			return new ResponseEntity<String>("Welcome " + empName, HttpStatus.OK);
		}
		return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/checkOut/{empId}")
	public ResponseEntity<Attendance> getAttendanceByEmp(@PathVariable long empId) {
		Attendance records = attendanceServ.saveCheckOutEntry(empId);
		if (records != null)
			return new ResponseEntity<>(records, HttpStatus.OK);
		return new ResponseEntity<Attendance>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/endShift")
	public ResponseEntity<List<Attendance>> endShift(){
		List<Attendance> collect = attendanceServ.endWorkingShiftCall();
		return new ResponseEntity<>(collect, HttpStatus.OK);
	}

}

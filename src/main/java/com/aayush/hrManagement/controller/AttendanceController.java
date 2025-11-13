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
	public ResponseEntity<?> showAll() {
		List<Attendance> attendanceList = attendanceServ.getAllEntry();
		if (attendanceList.isEmpty()) {
			return new ResponseEntity<String>("No employee found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Attendance>>(attendanceList, HttpStatus.OK);
	}

	@GetMapping("/checkIn/{empId}")
	public ResponseEntity<String> checkIn(@PathVariable long empId) {
		Attendance entryResult = attendanceServ.saveCheckInEntry(empId);
		if (entryResult != null) {
			String empName = employeeServ.getEmployeeById(empId).get().getFirstName();
			return new ResponseEntity<String>("Welcome " + empName, HttpStatus.OK);
		}
		return new ResponseEntity<String>("User With employeeId: " + empId + " does'n exist", HttpStatus.NOT_FOUND);
	}

	@GetMapping("/checkOut/{empId}")
	public ResponseEntity<?> getAttendanceByEmp(@PathVariable long empId) {
		Attendance records = attendanceServ.saveCheckOutEntry(empId);
		if (records != null)
			return new ResponseEntity<Attendance>(records, HttpStatus.OK);
		return new ResponseEntity<String>("Employee " + empId + " is not registered", HttpStatus.NOT_FOUND);
	}

	@GetMapping("/endShift")
	public ResponseEntity<?> endShift() {
		try {
			List<Attendance> collect = attendanceServ.endWorkingShiftCall();
			return new ResponseEntity<List<Attendance>>(collect, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

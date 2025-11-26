package com.aayush.hrManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
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

	@GetMapping("/checkIn")
	public ResponseEntity<String> checkIn() {
		Authentication authenticate = SecurityContextHolder.getContext().getAuthentication();
		String email = authenticate.getName();
		Attendance entryResult = attendanceServ.saveCheckInEntry(email);
		if (entryResult != null) {	
			String empName = employeeServ.getEmployeeByEmail(email).get().getFirstName();
			return new ResponseEntity<String>("Welcome " + empName, HttpStatus.OK);
		}
		return new ResponseEntity<String>("User With employeeId: " + employeeServ.getEmployeeByEmail(email).get().getEmployeeId() + " doesn't exist", HttpStatus.NOT_FOUND);
	}

	@GetMapping("/checkOut")
	public ResponseEntity<?> getAttendanceByEmp() {
		Authentication authenticate = SecurityContextHolder.getContext().getAuthentication();
		String email = authenticate.getName();
		Attendance records = attendanceServ.saveCheckOutEntry(email);
		if (records != null)
			return new ResponseEntity<Attendance>(records, HttpStatus.OK);
		return new ResponseEntity<String>("Employee " + employeeServ.getEmployeeByEmail(email).get().getEmployeeId() + " is not registered or already checked-out", HttpStatus.NOT_FOUND);
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

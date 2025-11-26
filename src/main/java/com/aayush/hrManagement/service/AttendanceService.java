package com.aayush.hrManagement.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aayush.hrManagement.entity.Attendance;
import com.aayush.hrManagement.entity.Employee;
import com.aayush.hrManagement.repository.AttendanceRepository;

@Service
public class AttendanceService {

	@Autowired
	private AttendanceRepository attendanceRepo;

	@Autowired
	private EmployeeService employeeServ;

	public List<Attendance> getAllEntry() {
		return attendanceRepo.findAll();
	}

	public Attendance saveCheckInEntry(String email) {
		if (employeeServ.getEmployeeByEmail(email).isPresent()) {
			long empId = employeeServ.getEmployeeByEmail(email).get().getEmployeeId();
			Attendance attendance = new Attendance();
			LocalTime currentTime = LocalTime.now();

			attendance.setEmployeeId(empId);
			attendance.setDate(LocalDate.now());
			attendance.setCheckInTime(currentTime);

			if (currentTime.isAfter(LocalTime.of(9, 30))) {
				attendance.setStatus(Attendance.Status.LATE);
			} else {
				attendance.setStatus(Attendance.Status.PRESENT);
			}
			return attendanceRepo.save(attendance);
		}
		return null;
	}

	public Attendance saveCheckOutEntry(String email) {
		if (employeeServ.getEmployeeByEmail(email).isPresent()) {
			long empId = employeeServ.getEmployeeByEmail(email).get().getEmployeeId();
			Optional<Attendance> collect = attendanceRepo.findByEmployeeIdAndDate(empId, LocalDate.now());
			LocalTime currentTime = LocalTime.now();
			if (collect.isPresent() && collect.get().getCheckOutTime() == null) {
				int workingHours = currentTime.getHour() - collect.get().getCheckInTime().getHour();
				collect.get().setCheckOutTime(currentTime);
				if (workingHours >= 5) {
					collect.get().setStatus(Attendance.Status.HALF_DAY);
				} else {
					collect.get().setStatus(Attendance.Status.ABSENT);
				}
				return attendanceRepo.save(collect.get());
			}
		}
		return null;
	}

	public List<Attendance> endWorkingShiftCall() {
		List<Attendance> todayEntries = attendanceRepo.findByDate(LocalDate.now());
		List<Employee> allEmployee = employeeServ.getAllEmployee();
		for (Employee emp : allEmployee) {
			boolean hasRow = todayEntries.stream().anyMatch(em -> em.getEmployeeId() == emp.getEmployeeId());
			if (!hasRow) {
				Attendance newEntry = new Attendance();
				newEntry.setDate(LocalDate.now());
				newEntry.setEmployeeId(emp.getEmployeeId());
				newEntry.setStatus(Attendance.Status.ABSENT);
				todayEntries.add(newEntry);
			}
		}
		for (Attendance entry : todayEntries) {
			if (entry.getCheckOutTime() == null) {
				if (entry.getCheckInTime() == null) {
					entry.setStatus(Attendance.Status.ABSENT);
				} else {
					entry.setStatus(Attendance.Status.HALF_DAY);
				}
			}
		}
		List<Attendance> result = attendanceRepo.saveAll(todayEntries);
		return result;
	}
}

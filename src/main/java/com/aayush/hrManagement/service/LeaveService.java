package com.aayush.hrManagement.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aayush.hrManagement.entity.Employee;
import com.aayush.hrManagement.entity.Leave;
import com.aayush.hrManagement.repository.LeaveRepository;

@Service
public class LeaveService {

	@Autowired
	private LeaveRepository leaveRepo;

	@Autowired
	private EmployeeService employeeService;

	public List<Leave> getAll() {
		return leaveRepo.findAll();
	}

	public Leave addLeave(Leave leave) {
		Optional<Employee> empIn = employeeService.getEmployeeById(leave.getEmployeeId());
		long empId = leave.getEmployeeId();
		Leave collect = leaveRepo.findByEmployeeId(empId).orElse(null);
		if(collect != null) {
			if(!leave.getStartDate().isAfter(collect.getEndDate())) {
				return null;
			}
		}
		
		if (empIn.isPresent()) {
			Employee employee = empIn.get();
			leave.setAppliedDate(LocalDate.now());

			if (leave.getStatus() == null) {
				leave.setStatus(Leave.Status.PENDING);
			}

			leave.setLeaveDays(leave.calcualteLeaveDays(leave.getStartDate(), leave.getEndDate()));

			long sickLeaveRemaining = employee.getLeaveQuota().getSickLeaveQuota();
			long casualLeaveRemaining = employee.getLeaveQuota().getCasualLeaveQuota();
			long annualLeaveRemaining = employee.getLeaveQuota().getAnnualLeaveQuota();

			if (leave.getLeaveType().equals(Leave.LeaveType.SICK) && leave.getLeaveDays() <= sickLeaveRemaining) {
				employee.getLeaveQuota().setSickLeaveQuota(sickLeaveRemaining - leave.getLeaveDays());
			} else if (leave.getLeaveType().equals(Leave.LeaveType.CASUAL)
					&& leave.getLeaveDays() <= casualLeaveRemaining) {
				employee.getLeaveQuota().setCasualLeaveQuota(casualLeaveRemaining - leave.getLeaveDays());
			} else if (leave.getLeaveType().equals(Leave.LeaveType.ANNUAL)
					&& leave.getLeaveDays() <= annualLeaveRemaining) {
				employee.getLeaveQuota().setAnnualLeaveQuota(annualLeaveRemaining - leave.getLeaveDays());
			} else {
				return null;
			}
			Employee updateLeaveQuota = employeeService.saveEmployee(employee);
			if (updateLeaveQuota != null) {
				return leaveRepo.save(leave);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public Leave updateDecisionById(long id, Leave leave) {
		Leave collect = leaveRepo.findById(id).get();
		collect.setStatus(leave.getStatus());
		collect.setDecisionDate(LocalDate.now());
		return leaveRepo.save(collect);
	}

}

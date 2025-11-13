package com.aayush.hrManagement.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*; 

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long employeeId;
	
	@Column(nullable = false)
	private String firstName;
	private String lastName;
	private LocalDate dateOfJoining;
	
	@Column(nullable = false, unique = true)
	private String email;
	private String phoneNumber;
	private String address;
	private String department;
	private String jobTitle;
	private double salary;
	private String employmentStatus;
	
	@Embedded
    private LeaveQuota leaveQuota = new LeaveQuota();
}

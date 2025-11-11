package com.aayush.hrManagement.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "attendance")
public class Attendance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long attendanceId;
	@Column(nullable = false, unique = true)
	private long employeeId;
	private LocalDate date;
	private LocalTime checkInTime;
	private LocalTime checkOutTime;
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Status status;
	 
	public enum Status{
		PRESENT, ABSENT, HALF_DAY, LATE
	}
}

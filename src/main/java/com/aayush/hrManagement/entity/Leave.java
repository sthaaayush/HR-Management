package com.aayush.hrManagement.entity;

import java.time.LocalDate;
import java.time.Period;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "leave_records")
public class Leave{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long leaveId;

    @Column(nullable = false)
    private long employeeId;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false, length = 150)
    private String reason;

    private LocalDate appliedDate;
    private LocalDate decisionDate;
    
    private long leaveDays;


    public enum LeaveType {
        SICK, CASUAL, UNPAID, ANNUAL
    }

    public enum Status {
        PENDING, APPROVED, REJECTED
    }
    
    public int calcualteLeaveDays(LocalDate startDate, LocalDate endDate) {
    	return Period.between(startDate, endDate).getDays() + 1;
    }
}

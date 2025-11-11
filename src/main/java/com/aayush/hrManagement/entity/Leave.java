package com.aayush.hrManagement.entity;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "leave_records")
public class Leave {

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

    @Column(nullable = false)
    private String reason;

    private LocalDate appliedDate;
    private LocalDate decisionDate;

    @Embedded
    private LeaveQuota leaveQuota = new LeaveQuota();

    public enum LeaveType {
        SICK, CASUAL, UNPAID, ANNUAL
    }

    public enum Status {
        PENDING, APPROVED, REJECTED
    }
}

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
class LeaveQuota {
    private long sickLeaveQuota = 18;
    private long casualLeaveQuota = 16;
    private long annualLeaveQuota = 10;
}

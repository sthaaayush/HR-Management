package com.aayush.hrManagement.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveQuota {
    private long sickLeaveQuota = 18;
    private long casualLeaveQuota = 16;
    private long annualLeaveQuota = 10;
}

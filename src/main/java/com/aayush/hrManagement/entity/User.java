package com.aayush.hrManagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Builder
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;
	
	@Column(nullable = false, unique = true)
	private String userEmail;
	
	@Column(nullable = false)
	private String userPassword;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserRole userRole;
	
	private boolean activeStatus;
	
	public enum UserRole{
		ROLE_ADMIN, 
		ROLE_HR, 
		ROLE_MANAGER, 
		ROLE_EMPLOYEE
	}
}

package com.aayush.hrManagement.healthCheck;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {
	@GetMapping("/healthCheck")
	public String hi() {
		return "Health Check: All okay";
	}
}

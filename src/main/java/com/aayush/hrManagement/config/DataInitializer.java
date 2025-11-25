package com.aayush.hrManagement.config;

import com.aayush.hrManagement.entity.User;
import com.aayush.hrManagement.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;

    public DataInitializer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = "admin@example.com";
        if (userService.getByUserEmail(adminEmail).isEmpty()) {
            User admin = User.builder()
                    .userEmail(adminEmail)
                    .userPassword("admin123")
                    .userRole(User.UserRole.ROLE_ADMIN)
                    .activeStatus(true)
                    .build();

            userService.addUser(admin);
            System.out.println("Created default admin user: " + adminEmail + " / admin123");
        }else {
        	System.out.println(adminEmail + " already exist in database");
        }
    }
}

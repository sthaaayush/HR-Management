package com.aayush.hrManagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aayush.hrManagement.entity.Employee;
import com.aayush.hrManagement.entity.User;
import com.aayush.hrManagement.repository.EmployeeRepository;
import com.aayush.hrManagement.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private EmployeeRepository employeeRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<User> getAllUser() {
		return userRepo.findAll();
	}

	public User addUser(User user) {
		user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
		return userRepo.save(user);
	}

	public List<User> bulkAddUser(List<Long> ids) {
		List<User> customUser = new ArrayList<>();
		List<Employee> existingEmployee = employeeRepo.findAllById(ids);
		for (Employee newEmp : existingEmployee) {
			if (newEmp.isEmploymentStatus() && userRepo.findByUserEmail(newEmp.getEmail()).isEmpty()) {
				User user = new User();
				user.setUserEmail(newEmp.getEmail());
				String plainPassword = newEmp.getFirstName()
						+ newEmp.getPhoneNumber().substring(newEmp.getPhoneNumber().length() - 3);
				user.setUserPassword(passwordEncoder.encode(plainPassword));
				user.setUserRole(User.UserRole.ROLE_EMPLOYEE);
				user.setActiveStatus(true);
				customUser.add(user);
			}
		}
		return userRepo.saveAll(customUser);
	}

	public User updateUser(long id, User updatedUser) {
		Optional<User> userInDb = userRepo.findById(id);
		if (userInDb.isPresent()) {
			User user = userInDb.get();
			if (updatedUser.getUserEmail() != null)
				user.setUserEmail(updatedUser.getUserEmail());
			if (updatedUser.getUserPassword() != null)
				user.setUserPassword(passwordEncoder.encode(updatedUser.getUserPassword()));
			if (updatedUser.getUserRole() != null)
				user.setUserRole(updatedUser.getUserRole());
			user.setActiveStatus(updatedUser.isActiveStatus());
			return userRepo.save(user);
		}
		return null;
	}

	public Optional<User> getByUserEmail(String email) {
		return userRepo.findByUserEmail(email);
	}

	public Optional<User> getByUserId(long id) {
		return userRepo.findById(id);
	}

	public void deleteByUserId(long id) {
		userRepo.deleteById(id);
	}
}

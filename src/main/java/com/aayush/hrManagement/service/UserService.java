package com.aayush.hrManagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aayush.hrManagement.entity.User;
import com.aayush.hrManagement.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<User> getAllUser(){
		return userRepo.findAll();
	}
	
	public User addUser(User user) {
		user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
		return userRepo.save(user);
	}
	
	public User updateUser(long id, User updatedUser) {
		Optional<User> userInDb = userRepo.findById(id);
		if(userInDb.isPresent()) {
			User user = userInDb.get();
			user.setUserEmail(updatedUser.getUserEmail());
            if (updatedUser.getUserPassword() != null) {
                user.setUserPassword(passwordEncoder.encode(updatedUser.getUserPassword()));
            }
            user.setUserRole(updatedUser.getUserRole());
            user.setActiveStatus(updatedUser.isActiveStatus());
            return userRepo.save(user);
		}
		return null;
	}
	
	public Optional<User> getByUserEmail(String email){
		return userRepo.findByUserEmail(email);
	}
	
	public Optional<User> getByUserId(long id) {
		return userRepo.findById(id);
	}
	
	public void deleteByUserId(long id) {
		userRepo.deleteById(id);
	}
}

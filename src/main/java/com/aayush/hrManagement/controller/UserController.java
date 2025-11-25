package com.aayush.hrManagement.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aayush.hrManagement.entity.User;
import com.aayush.hrManagement.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userServ;
	
	@GetMapping("/getUser")
	public ResponseEntity<?> getAllUser(){
		List<User> allUser = userServ.getAllUser();
		if(allUser.isEmpty()) {
			return new ResponseEntity<String>("No Data Found", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(allUser, HttpStatus.OK);
	}
	
	@PostMapping("/addUser")
	public ResponseEntity<?> addNewUser(@RequestBody User user){
		try {
			User savedUser = userServ.addUser(user);
			return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/createEmployeeUser")
	public ResponseEntity<?> addEmployeeUser(@RequestBody List<Long> ids){
		try {
			List<User> savedEmployeeUser = userServ.bulkAddUser(ids);
			return new ResponseEntity<List<User>>(savedEmployeeUser, HttpStatus.CREATED);		
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/searchUser/{id}")
	public ResponseEntity<?> searchUserById(@PathVariable long id){
		Optional<User> foundUser = userServ.getByUserId(id); 
		if(foundUser.isPresent()) {
			return new ResponseEntity<User>(foundUser.get(), HttpStatus.OK);
		}
		return new ResponseEntity<String>("User with UserId "+id+" doesn't exist", HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable long id){
		try {
			userServ.deleteByUserId(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/updateUser/{id}")
	public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody User user){
		User updatedUser = userServ.updateUser(id, user);
		if(updatedUser != null) {
			return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
		}
		return new ResponseEntity<String>("UserID: "+user.getUserId()+" didn't updated", HttpStatus.NOT_FOUND);
	}
	
}

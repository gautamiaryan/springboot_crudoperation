package com.springboot.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.dto.UserDTO;
import com.springboot.exception.ResourceNotFoundException;
import com.springboot.model.User;
import com.springboot.repo.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	@PostMapping("/userins")
	public User insertUser(@Valid @RequestBody User user) {
		
		return userRepository.save(user);
	}
	@GetMapping("/users/{id}")
	public User getUserById(@PathVariable(value="id") Long userId) {
		
		return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		
	}
	
	@PutMapping("/users/{id}")
	public User updateUser(@PathVariable(value="id") Long userId,@Valid @RequestBody User userDetails) {
		
		User user=userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		
		user.setName(userDetails.getName());
		user.setEmailId(userDetails.getEmailId());
		
		User updateUser=userRepository.save(user);
		
		return updateUser;
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable(value="id") Long userId){
		
		
		User user=userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
	 
		userRepository.delete(user);
		
		return ResponseEntity.ok().build();
	}
	
	
}

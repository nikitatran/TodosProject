package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;

import io.swagger.annotations.ApiOperation;

@RequestMapping("/api")
@RestController
public class UserController {
	
	@Autowired
	UserRepository repo;
	@GetMapping("/user")
	@ApiOperation( value = "Find User by id",
	   notes = "Provide an id to look up a user in the database",
	   response = User.class)
	public ResponseEntity<?> getAllUsers() {
		List<User> list = repo.findAll();
		
		return ResponseEntity.status(200)
							 .body(list);
	}
	
	@PostMapping("/user")
	@ApiOperation( value = "Add User",
	   notes = "Add new user with id to database",
	   response = User.class)
	public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
		
		// regardless of the initial id, won't override something that already exists
		user.setId(-1L);
		
		User created = repo.save(user);
		
		return ResponseEntity.status(201)
							 .body(created);
			
	}
	
	@PutMapping("/user")
	@ApiOperation( value = "Update User",
	   notes = "Update user (if exists) in the database",
	   response = User.class)
	public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
		
		Optional<User> found =  repo.findById(user.getId());

		if( found.isPresent() ) {
			User updated = repo.save(user);
			
			return ResponseEntity.status(200)
								 .body(updated);
		}
		else {

			return ResponseEntity.status(400)
								 .body(new User());
		}
	}
	
	@GetMapping("/user/{id}")
	@ApiOperation( value = "Get User by id",
	   notes = "Search for user (if exists) in the database",
	   response = User.class)
	public ResponseEntity<?> getUserById(@PathVariable Long id) throws ResourceNotFoundException {
		if(!repo.existsById(id)) {
			throw new ResourceNotFoundException("User with id " +id + " not found");
		}
	    User user = repo.findById(id).get();
	    
	    return ResponseEntity.status(200)
	                        .body(user);

	}

	// FIXME: not implemented yet
//	@PatchMapping("/user/{id}")
//	public ResponseEntity<User> updateUserName(@PathVariable Long id) {
//		Optional<User> user = repo.findById(id);
//		
//		if(user.isPresent()) {
//			return ResponseEntity.status(200)
//								 .body(user.get());
//		}
//		else {
//			return ResponseEntity.status(400)
//								 .body(new User() );
//		}
//	}
	
	@DeleteMapping("/user/{id}")
	@ApiOperation( value = "Delete User by id",
	   notes = "Delete user (if exists) in the database",
	   response = User.class)
	public ResponseEntity<User> deleteUser(@PathVariable Long id) throws ResourceNotFoundException {
		
		Optional<User> user = repo.findById(id);
		
		if(user.isPresent()) {
			repo.deleteById(id);
			return ResponseEntity.status(200)
								 .body(user.get());
		}
		else {
			throw new ResourceNotFoundException("User with id " +id + " not found");
		}
	}
	
	
}

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

import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;

@RequestMapping("/api")
@RestController
public class UserController {
	
	@Autowired
	UserRepository repo;
	@GetMapping("/user")
	public ResponseEntity<?> getAllUsers(@PathVariable User user) {
		
		User getAll = (User) repo.findAll();
		
		if(getAll == null) {
			return ResponseEntity.status(500)
								 .body(getAll);
		}
		
		return ResponseEntity.status(201)
							 .body(getAll);
	}
	
	@PostMapping("/user")
	public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
		
		// regardless of the initial id, won't override something that already exists
		user.setId(-1L);
		
		User created = repo.save(user);
		
		return ResponseEntity.status(201)
							 .body(created);
			
	}
	
	@PutMapping("/user")
	public @ResponseBody String updateUser(@RequestBody User user) {
		
		Optional<User> found =  repo.findById(user.getId());

		if( found.isPresent() ) {
			User updated = repo.save(user);
			
			return "Saved: " + updated;
		}
		else {
			return "Could not update user, id = " + user.getId() + " does not exists";
		}
	}
	
	@GetMapping("/user/{id}/todo/")
	public ResponseEntity<?> getUser(@PathVariable Long id) {
	    
	    User user = repo.findById(id).get();
	    
	    return ResponseEntity.status(200)
	                        .body(user.getTodos());

	}

	@PatchMapping("/user/patch")
	public ResponseEntity<User> updateUserName(@PathVariable Long id) {
		Optional<User> user = repo.findById(id);
		
		if(user.isPresent()) {
			return ResponseEntity.status(200)
								 .body(user.get());
		}
		else {
			return ResponseEntity.status(400)
								 .body(new User() );
		}
	}
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable Long id) {
		
		Optional<User> user = repo.findById(id);
		
		if(user.isPresent()) {
			repo.deleteById(id);
			return ResponseEntity.status(200)
								 .body(user.get());
		}
		else {
			return ResponseEntity.status(400)
								 .body(new User() );
		}
	}
	
	
}

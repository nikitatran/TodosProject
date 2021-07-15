package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Todo;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.TodosRepository;
import com.cognixia.jump.repository.UserRepository;


@RequestMapping("/api")
@RestController
public class TodoController {
	
	@Autowired
	TodosRepository todosRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	UserController userController;
	
	@GetMapping("/user/{userId}/todolist")
	public ResponseEntity<?> getUserTodolist(@PathVariable long userId) throws ResourceNotFoundException{
		if(!userRepo.existsById(userId)) {
			throw new ResourceNotFoundException("User with user id " + userId + " not found.");
		}
		List<Todo> todolist = userRepo.findById(userId).get().getTodos();
		return ResponseEntity.status(200).body(todolist);
	}
	
	@GetMapping("/user/{userId}/todo")
	public ResponseEntity<?> getUserTodoById(@PathVariable long userId, @RequestParam int id) throws ResourceNotFoundException{
		if(!userRepo.existsById(userId)) {
			throw new ResourceNotFoundException("User with user id " + userId + " not found.");
		}
		
		List<Todo> todolist = userRepo.findById(userId).get().getTodos();
		
		if(todolist.isEmpty() || id > todolist.size() || id <= 0) {
			throw new ResourceNotFoundException("Todo with id " + id + " not found.");
		}

		return ResponseEntity.status(200).body(todolist.get(id-1));
	}
	
	@PostMapping("/user/{userId}/todo")
	public ResponseEntity<?> addTodo(@PathVariable long userId, @Valid @RequestBody Todo todo) throws ResourceNotFoundException{
		todo.setId(-1);
		User user = (User) userController.getUserById(userId).getBody();
		
		todo.setUser(user);
		
		Todo newTodo = todosRepo.save(todo);
		
		return ResponseEntity.status(200).body(newTodo);
	}
	
	//TODO
	@DeleteMapping("/user/{userId}/todo")
	public ResponseEntity<?> deleteUserTodoById(@PathVariable long userId, @RequestParam int id) throws ResourceNotFoundException{
		Todo deleted = (Todo) getUserTodoById(userId, id).getBody();
		todosRepo.deleteTodo(deleted.getId());

		return ResponseEntity.status(200).body(deleted);
		
	}
	
	@PutMapping("/user/{userId}/todo")
	public ResponseEntity<?> updateUserTodoById(@PathVariable long userId, @Valid @RequestBody Todo todo) throws ResourceNotFoundException{
		Todo updated = (Todo) getUserTodoById(userId, todo.getId()).getBody();
		todo.setUser(updated.getUser());
		todosRepo.save(todo);
		
		return ResponseEntity.status(200).body(todo);
	}
	
	

}

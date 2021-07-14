package com.cognixia.jump.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.repository.UserRepository;

@RequestMapping("/api")
@RestController
public class UserController {
	
	@Autowired
	UserRepository repo;
	
	
}

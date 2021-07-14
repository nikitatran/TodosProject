package com.cognixia.jump.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.repository.TodosRepository;


@RequestMapping("/api")
@RestController
public class TodoController {
	
	@Autowired
	TodosRepository repo;
}

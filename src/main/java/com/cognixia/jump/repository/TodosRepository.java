package com.cognixia.jump.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognixia.jump.model.Todo;

public interface TodosRepository extends JpaRepository<Todo, Integer>{

}

package com.cognixia.jump.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cognixia.jump.model.Todo;

public interface TodosRepository extends JpaRepository<Todo, Integer>{
	@Transactional
	@Modifying
	@Query("delete from Todo t where t.id=:id")
	int deleteTodo(@Param("id") int id);
}

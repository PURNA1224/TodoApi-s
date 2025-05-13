package com.talentXp.todoApplication.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.talentXp.todoApplication.entity.Todo;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Integer>{
}

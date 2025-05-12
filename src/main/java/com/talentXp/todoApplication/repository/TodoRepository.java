package com.talentXp.todoApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talentXp.todoApplication.entity.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer>{
}

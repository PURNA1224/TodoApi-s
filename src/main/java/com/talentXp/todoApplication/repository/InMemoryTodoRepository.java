package com.talentXp.todoApplication.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.talentXp.todoApplication.entity.Todo;

@Repository
public class InMemoryTodoRepository {
	
	public static Map<Integer, Todo> todoInmemoryDB = new HashMap<>();
	
	static {
		todoInmemoryDB.put(1 , new Todo(1, "Java", "Learn about Aspect Oriented Programming(AOP)", LocalDate.now(), LocalDate.now()));
		todoInmemoryDB.put(2 , new Todo(2, "Java", "Learn about Threads", LocalDate.now(), LocalDate.now()));
		todoInmemoryDB.put(3 , new Todo(3, "Python", "Learn about Data Types", LocalDate.now(), LocalDate.now()));
	}
	
	public List<Todo> getAll(){
		return new ArrayList<>(todoInmemoryDB.values());
//		return todoInmemoryDB.values().stream().collect(Collectors.toList());
	}
	
	public int addTodo(Todo todo) {
		
		if(todoInmemoryDB.get(todo.getId()) != null) {
			return -1;
		}
		todoInmemoryDB.put(todo.getId(), new Todo(todo.getId(), todo.getTitle(), todo.getDescription(), todo.getStartDate(), todo.getEndDate()));
		return todo.getId();
	}
	
	public int updateTodo(Todo todo) {
		if(todoInmemoryDB.get(todo.getId()) == null) {
			return -1;
		}
		todoInmemoryDB.put(todo.getId(), new Todo(todo.getId(), todo.getTitle(), todo.getDescription(), todo.getStartDate(), todo.getEndDate()));
		return todo.getId();
		
	}

	public int deleteTodo(int id) {
		if(todoInmemoryDB.get(id) == null) {
			return -1;
		}
		todoInmemoryDB.remove(id);
		return id;
	}
	
	public int updateItem(Todo todo) {
		if(todoInmemoryDB.get(todo.getId()) == null) {
			return -1;
		}
		
		Todo newTodo = todoInmemoryDB.get(todo.getId());
		System.out.println(newTodo.getTitle());
		if(todo.getTitle() != null)
			newTodo.setTitle(todo.getTitle()); 
		if(todo.getDescription() != null)
			newTodo.setDescription(todo.getDescription());
		if(todo.getStartDate() != null)
			newTodo.setStartDate(todo.getStartDate());
		if(todo.getEndDate() != null)
			newTodo.setStartDate(todo.getEndDate());
			
		todoInmemoryDB.put(newTodo.getId() ,newTodo);
		return newTodo.getId();
	}
}

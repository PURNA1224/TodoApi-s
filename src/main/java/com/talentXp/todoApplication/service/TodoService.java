package com.talentXp.todoApplication.service;

import java.util.List;

import com.talentXp.todoApplication.Pojo.CreateTodoRequestModel;
import com.talentXp.todoApplication.Pojo.TodoDto;


public interface TodoService {
	public List<TodoDto> getAllTodos();
	public TodoDto getTodoById(int id);
	public TodoDto addTodo(CreateTodoRequestModel todo);
	public TodoDto deleteTodo(int id);
	public TodoDto updateTodo(CreateTodoRequestModel todoReq, int id);
}

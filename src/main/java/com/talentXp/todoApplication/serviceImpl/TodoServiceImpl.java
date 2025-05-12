package com.talentXp.todoApplication.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talentXp.todoApplication.Pojo.CreateTodoRequestModel;
import com.talentXp.todoApplication.Pojo.TodoDto;
import com.talentXp.todoApplication.entity.Todo;
import com.talentXp.todoApplication.repository.TodoRepository;
import com.talentXp.todoApplication.service.TodoService;

@Service
public class TodoServiceImpl implements TodoService {
	
	@Autowired
	TodoRepository todoRepo;
	
	@Autowired
	ModelMapper mapper;
	
	@Override
	public List<TodoDto> getAllTodos() {
		
		List<Todo> todo=  todoRepo.findAll();
		List<TodoDto> todoDtos = new ArrayList<>();
		
		todo.forEach(thisTodo -> {
			todoDtos.add(mapper.map(thisTodo, TodoDto.class));
		});
		
		return todoDtos;
	}
	
	@Override
	public TodoDto getTodoById(int id) {
		Optional<Todo> todo = todoRepo.findById(id);
		if(todo.isEmpty())
			return null;
		TodoDto todoDto = mapper.map(todo, TodoDto.class);
		return todoDto;
	}

	@Override
	public TodoDto addTodo(CreateTodoRequestModel todoReq) {
		Todo todo = mapper.map(todoReq, Todo.class);
		todoRepo.save(todo);
		TodoDto todoDto = mapper.map(todo, TodoDto.class);
		return todoDto;
	}

	@Override
	public TodoDto deleteTodo(int id) {
		Optional<Todo> todo = todoRepo.findById(id);
		if(todo.isEmpty()) {
			return null;
		}
		else {
			todoRepo.deleteById(id);
			return mapper.map(todo, TodoDto.class);
		}
	}

	@Override
	public TodoDto updateTodo(CreateTodoRequestModel todoReq, int id) {
		Todo todo = mapper.map(todoReq, Todo.class);
		todo.setId(id);
		
		Todo savedTodo = todoRepo.save(todo);

		return mapper.map(savedTodo, TodoDto.class);
	}
}

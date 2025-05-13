package com.talentXp.todoApplication.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talentXp.todoApplication.Pojo.TodoDto;
import com.talentXp.todoApplication.entity.Todo;
import com.talentXp.todoApplication.exceptionHandler.TodoNotFoundException;
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
		
		Iterable<Todo> todo=  todoRepo.findAll();
		List<TodoDto> todoDtos = new ArrayList<>();
		
		todo.forEach(thisTodo -> {
			todoDtos.add(mapper.map(thisTodo, TodoDto.class));
		});
		
		return todoDtos;
	}
	
	@Override
	public TodoDto getTodoById(int id) throws TodoNotFoundException{
		Optional<Todo> todo = todoRepo.findById(id);
		if(todo.isEmpty())
			throw new TodoNotFoundException("Todo doesn't exist with id " + id);
		TodoDto todoDto = mapper.map(todo, TodoDto.class);
		return todoDto;
	}

	@Override
	public TodoDto addTodo(TodoDto todoReq) {
		Todo todo = mapper.map(todoReq, Todo.class);
		todoRepo.save(todo);
		TodoDto todoDto = mapper.map(todo, TodoDto.class);
		return todoDto;
	}

	@Override
	public TodoDto deleteTodo(int id) throws TodoNotFoundException {
		Optional<Todo> todo = todoRepo.findById(id);
		if(todo.isEmpty()) {
			throw new TodoNotFoundException("Todo doesn't exist with id " + id);
		}
		else {
			todoRepo.deleteById(id);
			return mapper.map(todo, TodoDto.class);
		}
	}

	@Override
	public TodoDto updateTodo(TodoDto todoReq, int id) throws TodoNotFoundException {
		
		Optional<Todo> checkTodo = todoRepo.findById(id);
		if(checkTodo.isEmpty()) {
			throw new TodoNotFoundException("Todo doesn't exist with id " + id);
		}
		
		Todo todo = mapper.map(todoReq, Todo.class);
		todo.setId(id);
		Todo savedTodo = todoRepo.save(todo);

		return mapper.map(savedTodo, TodoDto.class);
	}
}

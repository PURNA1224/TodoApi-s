package com.talentXp.todoApplication.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talentXp.todoApplication.Pojo.CreateTodoRequestModel;
import com.talentXp.todoApplication.Pojo.CreateTodoResponseModel;
import com.talentXp.todoApplication.Pojo.TodoDto;
import com.talentXp.todoApplication.exceptionHandler.UserServiceException;
import com.talentXp.todoApplication.service.TodoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/todo")
public class TodoController {
	
	@Autowired
	TodoService todoService;
	
	@Autowired
	ModelMapper mapper;
	
	//EveryOne
	@GetMapping("/getAllTodos")
	public ResponseEntity<List<CreateTodoResponseModel>> getAllTodos(){
		List<TodoDto> todoDtos = todoService.getAllTodos();
		
		List<CreateTodoResponseModel> todoResponseModel = new ArrayList<>();
		todoDtos.forEach(thistodo -> 
			todoResponseModel.add(mapper.map(thistodo, CreateTodoResponseModel.class))
		);
		
		return ResponseEntity.ok().body(todoResponseModel);
		
		
	}
	
	//EveryOne
	@GetMapping("/getTodo/{id}")
	public ResponseEntity<?> getTodoById(@PathVariable int id) {
		TodoDto todoDto = todoService.getTodoById(id);
		CreateTodoResponseModel todoResponseModel = mapper.map(todoDto, CreateTodoResponseModel.class);
		return ResponseEntity.ok().body(todoResponseModel);		
	}
	
	//Admin or logged in user, response should only access by Admin
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@PostAuthorize("hasRole('ADMIN')")
	@PostMapping("/createTodo")
	public ResponseEntity<?> addTodo(@Valid @RequestBody CreateTodoRequestModel todoRequest) {
		TodoDto todoReqDto = mapper.map(todoRequest, TodoDto.class);
		TodoDto todoDto = todoService.addTodo(todoReqDto);
		CreateTodoResponseModel todoResponse = mapper.map(todoDto, CreateTodoResponseModel.class);
		return ResponseEntity.ok().body(todoResponse);
	}
	
	//Admin or logged in user
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@PutMapping("/update")
	public ResponseEntity<?> updateTodo(@Valid @RequestBody CreateTodoRequestModel todoReq, @RequestParam int id) {
		TodoDto todoReqDto = mapper.map(todoReq, TodoDto.class);
		TodoDto todoDto = todoService.updateTodo(todoReqDto, id);
		CreateTodoResponseModel todoResponse = mapper.map(todoDto, CreateTodoResponseModel.class);
		return ResponseEntity.ok().body(todoResponse);
	}
	
	//Admin
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deleteTodo/{id}")
	public ResponseEntity<?> deleteTodo(@PathVariable int id) throws UserServiceException{
		TodoDto todoDto = todoService.deleteTodo(id);
 		CreateTodoResponseModel todoResponse = mapper.map(todoDto, CreateTodoResponseModel.class);
 		return ResponseEntity.ok().body(todoResponse);
	}
}

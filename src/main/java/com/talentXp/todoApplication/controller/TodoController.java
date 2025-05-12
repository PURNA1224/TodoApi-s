package com.talentXp.todoApplication.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
		try {
			TodoDto todoDto = todoService.getTodoById(id);
			CreateTodoResponseModel todoResponseModel = mapper.map(todoDto, CreateTodoResponseModel.class);
			return ResponseEntity.ok().body(todoResponseModel);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unable to find the todo item with ID " + id + " because no todo exists with this id" );
		}
	}
	
	//Admin or logged in user, response should only access by Admin
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@PostAuthorize("hasRole('ADMIN')")
	@PostMapping("/createTodo")
	public ResponseEntity<?> addTodo(@Valid @RequestBody CreateTodoRequestModel todoRequest) {
		try {
			TodoDto todoDto = todoService.addTodo(todoRequest);
			CreateTodoResponseModel todoResponse = mapper.map(todoDto, CreateTodoResponseModel.class);
			return ResponseEntity.ok().body(todoResponse);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists with this email address"); 
		}
	}
	
	//Admin or logged in user
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@PutMapping("/update")
	public ResponseEntity<?> updateTodo(@RequestBody CreateTodoRequestModel todoReq, @RequestParam int id) {
		try {
			TodoDto todoDto = todoService.updateTodo(todoReq, id);
			CreateTodoResponseModel todoResponse = mapper.map(todoDto, CreateTodoResponseModel.class);
			return ResponseEntity.ok().body(todoResponse);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unable to update the todo item because no todo exists with ID " + id);
		}
	}
	
	//Admin
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deleteTodo/{id}")
	public ResponseEntity<?> deleteTodo(@PathVariable int id) {
		try {
			TodoDto todoDto = todoService.deleteTodo(id);
			CreateTodoResponseModel todoResponse = mapper.map(todoDto, CreateTodoResponseModel.class);
			return ResponseEntity.ok().body(todoResponse);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unable to delete the todo item because no todo exists with ID " + id);
		}
	}
}

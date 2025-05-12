package com.talentXp.todoApplication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talentXp.todoApplication.annotations.Crypto;

@RestController
public class HomeController {
	
	@GetMapping("/home")
	@Crypto
	public String home() {
		return "Welcome to Todo Application";
	}
	
	@GetMapping("/getAllData")
	@Crypto
	public ResponseEntity<String> getAllData(@RequestParam String name, @RequestParam String name2) {
		return ResponseEntity.status(200).body("Hello "+name + ", "+name2);
	}
	
	@GetMapping("/getAllData2")
	@Crypto
	public ResponseEntity<String> getAllData(@RequestParam String name) {
		return ResponseEntity.status(200).body("Hello "+name);
	}
}

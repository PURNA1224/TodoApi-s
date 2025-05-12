package com.talentXp.todoApplication.Pojo;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateTodoRequestModel {
	
	@NotNull(message = "Title cannot be null")
	private String title;
	@NotNull(message = "Title cannot be null")
	@Size(min = 5, message = "Enter minimum 5 characters")
	private String description;
	@NotNull(message = "Start date cannot be null")
	private LocalDate startDate;
	@NotNull(message = "End date cannot be null")
	private LocalDate endDate;
	
	
	
	public CreateTodoRequestModel() {
		super();
	}



	public CreateTodoRequestModel(String title, String description, LocalDate startDate, LocalDate endDate) {
		super();
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public LocalDate getStartDate() {
		return startDate;
	}



	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}



	public LocalDate getEndDate() {
		return endDate;
	}



	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	
	
}

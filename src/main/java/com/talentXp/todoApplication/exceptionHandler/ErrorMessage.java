package com.talentXp.todoApplication.exceptionHandler;

import java.util.Date;

public class ErrorMessage {
	Date errorOccurredDate;
	String message;
	
	public ErrorMessage(Date errorOccurredDate, String message) {
		super();
		this.errorOccurredDate = errorOccurredDate;
		this.message = message;
	}
	public Date getErrorOccurredDate() {
		return errorOccurredDate;
	}
	public void setErrorOccurredDate(Date errorOccurredDate) {
		this.errorOccurredDate = errorOccurredDate;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}

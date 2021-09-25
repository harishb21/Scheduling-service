package com.ct.scheduling.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ScheduleExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object>
	handleException(Exception ex){
		
		ScheduleErrorResponse error = new ScheduleErrorResponse();
		error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setResponse(ex.getMessage());
		error.setTimestamp(System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler
	protected ResponseEntity<ScheduleErrorResponse> 
	handleMethodArgumentNotValid(ScheduleNotFoundException ex){
		
		ScheduleErrorResponse error = new ScheduleErrorResponse();
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setResponse(ex.getMessage());
		error.setTimestamp(System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		ScheduleErrorResponse error = new ScheduleErrorResponse();
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setResponse(ex.getMessage());
		error.setTimestamp(System.currentTimeMillis());
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}
	
}

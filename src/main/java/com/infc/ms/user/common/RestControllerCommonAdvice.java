package com.infc.ms.user.common;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.context.support.DefaultMessageSourceResolvable;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;

@RestControllerAdvice(basePackages = {"com.*"})
public class RestControllerCommonAdvice {

	@ExceptionHandler(WebExchangeBindException.class)
	public ResponseEntity<List<String>> handleWebExchangeBindException(WebExchangeBindException e) {
		List<String> errors = e.getBindingResult().getAllErrors().stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
		return ResponseEntity.badRequest().body(errors);
	}

	@ExceptionHandler(ServerWebInputException.class)
	public ResponseEntity<String> handleServerWebInputException(ServerWebInputException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}



}

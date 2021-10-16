package com.infc.ms.user.common;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.DefaultMessageSourceResolvable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

@ControllerAdvice(basePackages = {"com.*"})
@Log4j2
public class RestControllerCommonAdvice {



	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(WebExchangeBindException.class)
	public ResponseEntity<List<String>> handleWebExchangeBindException(WebExchangeBindException e) {
		log.info("------------WebExchangeBindException--------------");
		List<String> errors = e.getBindingResult().getAllErrors().stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
		return ResponseEntity.badRequest().body(errors);
	}

	@ExceptionHandler(ServerWebInputException.class)
	public ResponseEntity<String> handleServerWebInputException(ServerWebInputException e) {
		log.info("------------handleServerWebInputException--------------");

		return ResponseEntity.badRequest().body(e.getMessage());
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
		log.info("------------handleConstraintViolationException--------------");

		return ResponseEntity.badRequest().body(e.getMessage());
	}



}

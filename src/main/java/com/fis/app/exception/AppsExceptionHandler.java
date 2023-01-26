package com.fis.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.log4j.Log4j2;

@Log4j2
@ControllerAdvice
public class AppsExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> exception(Exception exception) {
		log.error(exception);
		return new ResponseEntity<>("Opps something wents wrong", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

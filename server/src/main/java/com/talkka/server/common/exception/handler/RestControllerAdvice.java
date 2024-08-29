package com.talkka.server.common.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.talkka.server.common.dto.ErrorRespDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class RestControllerAdvice {
	private static final String INTERNAL_SERVER_ERROR_MESSAGE = "Server has some error";

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorRespDto> handleMethodArgumentNotValidException(
		MethodArgumentNotValidException exception) {
		String message = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();

		return ResponseEntity.badRequest().body(ErrorRespDto.of(message));
	}

	@ExceptionHandler(AuthorizationDeniedException.class)
	public ResponseEntity<ErrorRespDto> handleAuthorizationDeniedException(AuthorizationDeniedException exception) {
		return new ResponseEntity<>(ErrorRespDto.of(exception.getMessage()), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorRespDto> handleRuntimeException(RuntimeException exception) {
		log.error("""
				Exception Class : {}
				Exception Message : {}
				Stack Trace : {}
				""",
			exception.getClass().getName(),
			exception.getMessage(),
			exception.getStackTrace()
		);
		return new ResponseEntity<>(ErrorRespDto.of(INTERNAL_SERVER_ERROR_MESSAGE), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

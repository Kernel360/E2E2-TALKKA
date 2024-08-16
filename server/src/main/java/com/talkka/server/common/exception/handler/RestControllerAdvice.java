package com.talkka.server.common.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.talkka.server.common.dto.ApiRespDto;
import com.talkka.server.common.exception.enums.InvalidEnumCodeException;
import com.talkka.server.common.exception.http.HttpBaseException;

@ControllerAdvice
public class RestControllerAdvice {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleMethodArgumentNotValidException(
		MethodArgumentNotValidException exception) {
		String message = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();

		return ResponseEntity.badRequest().body(message);
	}

	@ExceptionHandler(InvalidEnumCodeException.class)
	public ResponseEntity<String> handleInvalidEnumCodeException(InvalidEnumCodeException exception) {
		String message = exception.getMessage();

		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Deprecated
	@ExceptionHandler(HttpBaseException.class)
	public ResponseEntity<ApiRespDto<Void>> handleHttpException(HttpBaseException exception) {
		ApiRespDto<Void> responseDto = ApiRespDto.<Void>builder()
			.statusCode(exception.getStatusCode().value())
			.message(exception.getMessage())
			.build();

		return new ResponseEntity<>(
			responseDto,
			exception.getStatusCode()
		);
	}
}

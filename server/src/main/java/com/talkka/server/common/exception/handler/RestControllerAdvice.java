package com.talkka.server.common.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.talkka.server.common.dto.ApiRespDto;
import com.talkka.server.common.dto.ErrorRespDto;
import com.talkka.server.common.exception.enums.InvalidEnumCodeException;
import com.talkka.server.common.exception.http.HttpBaseException;

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

	@ExceptionHandler(InvalidEnumCodeException.class)
	public ResponseEntity<ErrorRespDto> handleInvalidEnumCodeException(InvalidEnumCodeException exception) {
		log.error("InvalidEnumCodeException: {}", exception.getMessage());
		return new ResponseEntity<>(ErrorRespDto.of(INTERNAL_SERVER_ERROR_MESSAGE), HttpStatus.INTERNAL_SERVER_ERROR);
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

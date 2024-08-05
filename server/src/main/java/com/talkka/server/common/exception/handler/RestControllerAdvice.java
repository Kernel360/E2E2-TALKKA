package com.talkka.server.common.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.talkka.server.common.dto.ApiRespDto;
import com.talkka.server.common.exception.CustomException;
import com.talkka.server.common.exception.http.HttpBaseException;

@ControllerAdvice
public class RestControllerAdvice {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiRespDto<Void>> handleMethodArgumentNotValidException(
		MethodArgumentNotValidException exception) {
		ApiRespDto<Void> responseDto = ApiRespDto.<Void>builder()
			.statusCode(400)
			.message(exception.getBindingResult().getAllErrors().get(0).getDefaultMessage())
			.build();

		return new ResponseEntity<>(
			responseDto,
			HttpStatus.BAD_REQUEST
		);
	}

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

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiRespDto<Void>> handleCustomException(CustomException exception) {
		ApiRespDto<Void> responseDto = ApiRespDto.<Void>builder()
			.statusCode(exception.getErrorCode().getCode())
			.message(exception.getErrorCode().getMessage())
			.build();

		return new ResponseEntity<>(
			responseDto,
			exception.getStatusCode()
		);
	}

}

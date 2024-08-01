package com.talkka.server.common.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.talkka.server.common.dto.ApiResponseDto;
import com.talkka.server.common.exception.CustomException;
import com.talkka.server.common.exception.http.HttpBaseException;

@ControllerAdvice
public class RestControllerAdvice {

	@ExceptionHandler(HttpBaseException.class)
	public ResponseEntity<ApiResponseDto<Void>> handleHttpException(HttpBaseException exception) {
		ApiResponseDto<Void> responseDto = ApiResponseDto.<Void>builder()
			.statusCode(exception.getStatusCode().value())
			.message(exception.getMessage())
			.build();

		return new ResponseEntity<>(
			responseDto,
			exception.getStatusCode()
		);
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiResponseDto<Void>> handleCustomException(CustomException exception) {
		ApiResponseDto<Void> responseDto = ApiResponseDto.<Void>builder()
			.statusCode(exception.getErrorCode().getCode())
			.message(exception.getErrorCode().getMessage())
			.build();

		return new ResponseEntity<>(
			responseDto,
			exception.getStatusCode()
		);
	}

}

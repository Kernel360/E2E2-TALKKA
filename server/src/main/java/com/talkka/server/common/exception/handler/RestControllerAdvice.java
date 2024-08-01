package com.talkka.server.common.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.talkka.server.common.dto.ApiRespDto;
import com.talkka.server.common.exception.CustomException;
import com.talkka.server.common.exception.http.HttpBaseException;

@ControllerAdvice
public class RestControllerAdvice {

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

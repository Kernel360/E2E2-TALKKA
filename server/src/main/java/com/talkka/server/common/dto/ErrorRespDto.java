package com.talkka.server.common.dto;

import jakarta.validation.constraints.NotNull;

public record ErrorRespDto(
	@NotNull
	String message) {

	public static ErrorRespDto of(Exception exception) {
		return new ErrorRespDto(exception.getMessage());
	}

	public static ErrorRespDto of(String message) {
		return new ErrorRespDto(message);
	}
}

package com.talkka.server.common.dto;

public record ErrorRespDto(
	String message) {

	public static ErrorRespDto of(Exception exception) {
		return new ErrorRespDto(exception.getMessage());
	}

	public static ErrorRespDto of(String message) {
		return new ErrorRespDto(message);
	}
}

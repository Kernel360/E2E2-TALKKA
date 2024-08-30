package com.talkka.server.admin.exception;

public class CollectBusRouteAlreadyExistsException extends RuntimeException {
	private static final String MESSAGE = "이미 수집하고 있는 노선입니다.";

	public CollectBusRouteAlreadyExistsException() {
		super(MESSAGE);
	}
}

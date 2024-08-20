package com.talkka.server.bus.exception;

public class RouteAlreadyExistsException extends RuntimeException {
	private static final String MESSAGE = "이미 존재하는 노선입니다. apiRouteId: ";

	public RouteAlreadyExistsException(String apiRouteId) {
		super(MESSAGE + apiRouteId);
	}
}

package com.talkka.server.bus.exception;

public class BusRouteNotFoundException extends RuntimeException {
	private static final String MESSAGE = "존재하지 않는 노선입니다. routeId: ";

	public BusRouteNotFoundException(Long routeId) {
		super(MESSAGE + routeId);
	}
}

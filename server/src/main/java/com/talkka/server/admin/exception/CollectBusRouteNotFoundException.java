package com.talkka.server.admin.exception;

public class CollectBusRouteNotFoundException extends RuntimeException {
	private static final String MESSAGE = "수집하고 있지 않은 노선입니다.";

	public CollectBusRouteNotFoundException() {
		super(MESSAGE);
	}
}

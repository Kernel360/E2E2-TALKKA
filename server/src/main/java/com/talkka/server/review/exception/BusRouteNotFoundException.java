package com.talkka.server.review.exception;

public class BusRouteNotFoundException extends RuntimeException {
	private static final String MESSAGE = "존재하지 않는 노선입니다.";

	public BusRouteNotFoundException() {
		super(MESSAGE);
	}
}

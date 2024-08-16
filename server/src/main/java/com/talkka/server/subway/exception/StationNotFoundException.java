package com.talkka.server.subway.exception;

public class StationNotFoundException extends RuntimeException {
	private static final String message = "존재하지 않는 지하철 역입니다. StationId: ";

	public StationNotFoundException(Long stationId) {
		super(message + stationId);
	}
}

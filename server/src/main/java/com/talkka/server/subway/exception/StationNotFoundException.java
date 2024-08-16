package com.talkka.server.subway.exception;

public class StationNotFoundException extends RuntimeException {
	private static final String MESSAGE = "존재하지 않는 지하철 역입니다. StationId: ";

	public StationNotFoundException(Long stationId) {
		super(MESSAGE + stationId);
	}
}

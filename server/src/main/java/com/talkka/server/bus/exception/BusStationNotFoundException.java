package com.talkka.server.bus.exception;

public class BusStationNotFoundException extends RuntimeException {
	private static final String MESSAGE = "존재하지 않는 정거장입니다. stationId: ";

	public BusStationNotFoundException(Long stationId) {
		super(MESSAGE + stationId);
	}
}

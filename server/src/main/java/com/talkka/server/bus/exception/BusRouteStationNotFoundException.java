package com.talkka.server.bus.exception;

public class BusRouteStationNotFoundException extends RuntimeException {
	private static final String MESSAGE = "존재하지 않는 경유 정류장입니다. busRouteStationId: ";

	public BusRouteStationNotFoundException(Long busRouteStationId) {
		super(MESSAGE + busRouteStationId);
	}
}

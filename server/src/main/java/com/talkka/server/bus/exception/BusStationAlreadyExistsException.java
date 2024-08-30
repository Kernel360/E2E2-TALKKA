package com.talkka.server.bus.exception;

public class BusStationAlreadyExistsException extends RuntimeException {
	private static final String MESSAGE = "이미 존재하는 정거장입니다. apiStationId: ";

	public BusStationAlreadyExistsException(String apiStationId) {
		super(MESSAGE + apiStationId);
	}
}

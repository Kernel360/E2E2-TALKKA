package com.talkka.server.bus.enums;

import lombok.Getter;

@Getter
public enum TurnStation {
	TURN_STATION("1"), NOT_TURN_STATION("0");

	private final String code;

	TurnStation(String code) {
		this.code = code;
	}

	public static TurnStation fromCode(String value) {
		return switch (value) {
			case "0" -> NOT_TURN_STATION;
			case "1" -> TURN_STATION;
			default -> throw new IllegalArgumentException("Invalid value: " + value); // DOMAIN EXCEPTION 으로 변환 필요
		};
	}
}

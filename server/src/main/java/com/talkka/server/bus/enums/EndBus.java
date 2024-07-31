package com.talkka.server.bus.enums;

import lombok.Getter;

@Getter
public enum EndBus {
	// "0 = RUNNING" or "1 = END"
	RUNNING("1"), END("0");

	private final String code;

	EndBus(String code) {
		this.code = code;
	}

	public static EndBus fromCode(String value) {
		return switch (value) {
			case "0" -> RUNNING;
			case "1" -> END;
			default -> throw new IllegalArgumentException("Invalid value: " + value); // DOMAIN EXCEPTION 으로 변환 필요
		};
	}
}

package com.talkka.server.bus.enums;

import lombok.Getter;

@Getter
public enum LowPlate {
	// "0 = NORMAL" or "1 = LOW"
	NORMAL("0"), LOW("1");

	private final String code;

	LowPlate(String code) {
		this.code = code;
	}

	public static LowPlate fromCode(String value) {
		return switch (value) {
			case "0" -> NORMAL;
			case "1" -> LOW;
			default -> throw new IllegalArgumentException("Invalid value: " + value); // DOMAIN EXCEPTION 으로 변환 필요
		};
	}
}

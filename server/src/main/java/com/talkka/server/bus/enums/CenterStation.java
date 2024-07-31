package com.talkka.server.bus.enums;

import lombok.Getter;

@Getter
public enum CenterStation {
	NOT_CENTER_STATION("0"),
	CENTER_STATION("1");

	private final String code;

	CenterStation(String code) {
		this.code = code;
	}

	public static CenterStation fromCode(String value) {
		return switch (value) {
			case "0" -> NOT_CENTER_STATION;
			case "1" -> CENTER_STATION;
			default -> throw new IllegalArgumentException("Invalid value: " + value); // DOMAIN EXCEPTION 으로 변환 필요
		};
	}
}

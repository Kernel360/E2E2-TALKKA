package com.talkka.server.bus.enums;

import lombok.Getter;

@Getter
public enum PlateType {
	// 0: 정보없음, 1: 소형승합차, 2: 중형승합차, 3: 대형승합차, 4: 2층버스
	UNKNOWN("0"), SMALL("1"), MEDIUM("2"), LARGE("3"), DOUBLE_DECKER("4");

	private final String code;

	PlateType(String code) {
		this.code = code;
	}

	public static PlateType fromCode(String value) {
		return switch (value) {
			case "0" -> UNKNOWN;
			case "1" -> SMALL;
			case "2" -> MEDIUM;
			case "3" -> LARGE;
			case "4" -> DOUBLE_DECKER;
			default -> throw new IllegalArgumentException("Invalid value: " + value); // DOMAIN EXCEPTION 으로 변환 필요
		};
	}
}

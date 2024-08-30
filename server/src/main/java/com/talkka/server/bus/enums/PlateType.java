package com.talkka.server.bus.enums;

import com.talkka.server.common.util.EnumCodeInterface;

import lombok.Getter;

@Getter
public enum PlateType implements EnumCodeInterface {
	// 0: 정보없음, 1: 소형승합차, 2: 중형승합차, 3: 대형승합차, 4: 2층버스
	UNKNOWN("0"), SMALL("1"), MEDIUM("2"), LARGE("3"), DOUBLE_DECKER("4");

	private final String code;

	PlateType(String code) {
		this.code = code;
	}
}

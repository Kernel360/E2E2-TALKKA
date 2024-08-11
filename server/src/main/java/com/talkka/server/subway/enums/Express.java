package com.talkka.server.subway.enums;

import com.talkka.server.common.util.EnumCodeInterface;

import lombok.Getter;

@Getter
public enum Express implements EnumCodeInterface {
	EXPRESS("급행", "0"), NORMAL("일반", "1");

	private final String type;
	private final String code;

	Express(String type, String code) {
		this.type = type;
		this.code = code;
	}
}

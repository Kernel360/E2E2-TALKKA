package com.talkka.server.bus.enums;

import com.talkka.server.common.util.EnumCodeInterface;

import lombok.Getter;

@Getter
public enum LowPlate implements EnumCodeInterface {
	// "0 = NORMAL" or "1 = LOW"
	NORMAL("0"), LOW("1");

	private final String code;

	LowPlate(String code) {
		this.code = code;
	}
}

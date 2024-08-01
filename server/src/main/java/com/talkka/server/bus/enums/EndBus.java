package com.talkka.server.bus.enums;

import com.talkka.server.common.util.EnumCodeInterface;

import lombok.Getter;

@Getter
public enum EndBus implements EnumCodeInterface {
	// "0 = RUNNING" or "1 = END"
	RUNNING("1"), END("0");

	private final String code;

	EndBus(String code) {
		this.code = code;
	}
}

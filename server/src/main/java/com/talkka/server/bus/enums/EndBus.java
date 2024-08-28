package com.talkka.server.bus.enums;

import com.talkka.server.common.util.EnumCodeInterface;

import lombok.Getter;

@Getter
public enum EndBus implements EnumCodeInterface {
	// "0 = RUNNING" or "1 = END"
	RUNNING("0"), END("1"), END_BUS_CODE2("2"), LENT_BUS_END("4");

	private final String code;

	EndBus(String code) {
		this.code = code;
	}
}

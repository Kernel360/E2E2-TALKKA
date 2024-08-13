package com.talkka.server.bus.enums;

import com.talkka.server.common.util.EnumCodeInterface;

import lombok.Getter;

@Getter
public enum CenterStation implements EnumCodeInterface {
	NOT_CENTER_STATION("N"),
	CENTER_STATION("Y");

	private final String code;

	CenterStation(String code) {
		this.code = code;
	}
}

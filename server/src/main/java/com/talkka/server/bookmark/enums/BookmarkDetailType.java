package com.talkka.server.bookmark.enums;

import com.talkka.server.common.util.EnumCodeInterface;

import lombok.Getter;

@Getter
public enum BookmarkDetailType implements EnumCodeInterface {
	// 0: 버스, 1: 지하철
	BUS("0"), SUBWAY("1");

	private final String code;

	BookmarkDetailType(String code) {
		this.code = code;
	}
}

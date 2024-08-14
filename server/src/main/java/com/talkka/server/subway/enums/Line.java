package com.talkka.server.subway.enums;

import com.talkka.server.common.util.EnumCodeInterface;

import lombok.Getter;

@Getter
public enum Line implements EnumCodeInterface {
	LINE_ONE("1호선", "1001"),
	LINE_TWO("2호선", "1002"),
	LINE_THREE("3호선", "1003"),
	LINE_FOUR("4호선", "1004"),
	LINE_FIVE("5호선", "1005"),
	LINE_SIX("6호선", "1006"),
	LINE_SEVEN("7호선", "1007"),
	LINE_EIGHT("8호선", "1008"),
	LINE_NINE("9호선", "1009"),
	LINE_UI("우이신설선", "1092"),
	LINE_GYEONGCHUN("경춘선", "1067"),
	LINE_GYEONGUI_JUNGANG("경의중앙선", "1063"),
	LINE_SUIN_BUNDANG("수인분당선", "1075"),
	LINE_SINBUNDANG("신분당선", "1077"),
	LINE_AIRPORT("공항철도", "1065"),
	LINE_GYEONGGANG("경강선", "1081"),
	LINE_SEOHAE("서해선", "1093"),
	LINE_GTX_A("GTX-A", "1032");

	private final String name;
	private final String code;

	Line(String name, String code) {
		this.name = name;
		this.code = code;
	}
}

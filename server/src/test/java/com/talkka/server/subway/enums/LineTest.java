package com.talkka.server.subway.enums;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.talkka.server.subway.exception.enums.InvalidLineEnumException;

class LineTest {

	@ParameterizedTest
	@ValueSource(strings = {
		"LINE_ONE", "LINE_TWO", "LINE_THREE", "LINE_FOUR", "LINE_FIVE", "LINE_SIX", "LINE_SEVEN", "LINE_EIGHT",
		"LINE_NINE", "LINE_UI", "LINE_GYEONGCHUN", "LINE_GYEONGUI_JUNGANG", "LINE_SUIN_BUNDANG",
		"LINE_SINBUNDANG", "LINE_AIRPORT", "LINE_GYEONGGANG", "LINE_SEOHAE", "LINE_GTX_A"
	})
	@DisplayName("ENUM 문자열과 일치하면 ENUM을 생성한다.")
	void valueOfEnumString(String enumCode) {
		// given
		// when
		Line actual = Line.valueOfEnumString(enumCode);
		// then
		assertNotNull(actual);
	}

	@ParameterizedTest
	@ValueSource(strings = {
		"LINE_TEN", "LINE_ELEVEN", "GTX_B", "GYEONGGANG_2", "LINE_XYZ", "INVALID_LINE"
	})
	@DisplayName("ENUM 문자열과 일치하지 않으면 예외를 발생시킨다.")
	void valueOfEnumStringNotMatch(String enumCode) {
		// given
		// when
		// then
		assertThrows(InvalidLineEnumException.class, () -> Line.valueOfEnumString(enumCode));
	}
}

package com.talkka.server.subway.enums;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.talkka.server.subway.exception.enums.InvalidExpressEnumException;

class ExpressTest {

	@ParameterizedTest
	@ValueSource(strings = {"NORMAL", "EXPRESS"})
	@DisplayName("ENUM 문자열과 일치하면 ENUM을 생성한다.")
	void valueOfEnumString(String enumValue) {
		// given
		// when
		Express actual = Express.valueOfEnumString(enumValue);
		// then
		assertNotNull(actual);
	}

	@ParameterizedTest
	@ValueSource(strings = {"FAST", "SLOW", "EXP", "REGULAR", "INVALID"})
	@DisplayName("ENUM 문자열과 일치하지 않으면 예외를 발생시킨다.")
	void valueOfEnumStringNotMatch(String enumValue) {
		// given
		// when
		// then
		assertThrows(InvalidExpressEnumException.class, () -> Express.valueOfEnumString(enumValue));
	}
}


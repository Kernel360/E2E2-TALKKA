package com.talkka.server.user.vo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.talkka.server.user.exception.InvalidEmailException;

class EmailTest {
	@ParameterizedTest
	@DisplayName("생성시 이메일 형식이 올바르지 않은 경우 InvalidEmailException 발생")
	@ValueSource(strings = {"invalid", "invalid@", "@invalid", "invalid@invalid", "invalid@invalid.",
		"invalid@invalid.c", "a@b.c"})
	void testInvalidEmail(String email) {
		assertThrows(InvalidEmailException.class, () -> new Email(email));
	}

	@ParameterizedTest
	@DisplayName("생성시 이메일 형식이 올바른 경우 객체 생성")
	@ValueSource(strings = {"test@test.com", "sungjpar@photogrammer.me"})
	void testValidEmail(String email) {
		// given
		// when
		Email actual = new Email(email);
		// then
		assertEquals(email, actual.email());
	}

	@ParameterizedTest
	@DisplayName("이메일 형식이 올바른 경우 isValid 메서드는 true 반환")
	@ValueSource(strings = {"test@test.com", "sungjpar@photogrammer.me"})
	void testIsValidTrue(String email) {
		// given
		// when
		boolean actual = Email.isValid(email);
		// then
		assertTrue(actual);
	}

	@ParameterizedTest
	@DisplayName("이메일 형식이 올바르지 않은 경우 isValid 메서드는 false 반환")
	@ValueSource(strings = {"invalid", "invalid@", "@invalid", "invalid@invalid", "invalid@invalid.",
		"invalid@invalid.c", "a@b.c"})
	void testIsValidFalse(String email) {
		// given
		// when
		boolean actual = Email.isValid(email);
		// then
		assertFalse(actual);
	}
}
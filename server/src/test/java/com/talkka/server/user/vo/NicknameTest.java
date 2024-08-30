package com.talkka.server.user.vo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.talkka.server.user.exception.InvalidNicknameException;

class NicknameTest {
	@ParameterizedTest
	@DisplayName("생성시 닉네임 형식이 올바르지 않은 경우 InvalidNicknameException 발생")
	@ValueSource(strings = {"", "a", "_abc", "test!", "test test", "ㄱㅋㅋ", "123456789012345678901234"})
	void testInvalidNickname(String nickname) {
		assertThrows(InvalidNicknameException.class, () -> new Nickname(nickname));
	}

	@ParameterizedTest
	@DisplayName("생성시 닉네임 형식이 올바른 경우 객체 생성")
	@ValueSource(strings = {"test", "test123", "테스트", "테스트123", "test테스트"})
	void testValidNickname(String nickname) {
		// given
		// when
		Nickname actual = new Nickname(nickname);
		// then
		assertEquals(nickname, actual.nickname());
	}

	@ParameterizedTest
	@DisplayName("닉네임 형식이 올바른 경우 isValid 메서드는 true 반환")
	@ValueSource(strings = {"test", "test123", "테스트", "테스트123", "test테스트"})
	void testIsValidTrue(String nickname) {
		// given
		// when
		boolean actual = Nickname.isValid(nickname);
		// then
		assertTrue(actual);
	}

	@ParameterizedTest
	@DisplayName("닉네임 형식이 올바르지 않은 경우 isValid 메서드는 false 반환")
	@ValueSource(strings = {"", "a", "_abc", "test!", "test test", "ㄱㅋㅋ", "123456789012345678901234"})
	void testIsValidFalse(String nickname) {
		// given
		// when
		boolean actual = Nickname.isValid(nickname);
		// then
		assertFalse(actual);
	}
}
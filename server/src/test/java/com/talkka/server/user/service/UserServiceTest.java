package com.talkka.server.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.talkka.server.user.dao.UserEntity;
import com.talkka.server.user.dao.UserRepository;
import com.talkka.server.user.dto.UserCreateDto;
import com.talkka.server.user.dto.UserDto;
import com.talkka.server.user.dto.UserUpdateDto;
import com.talkka.server.user.enums.Grade;
import com.talkka.server.user.exception.DuplicatedNicknameException;
import com.talkka.server.user.exception.UserNotFoundException;
import com.talkka.server.user.vo.Email;
import com.talkka.server.user.vo.Nickname;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	private UserDto getUserDto(Long userId) {
		Nickname nickname = new Nickname("nickname" + userId);
		Email email = new Email("email" + userId + "@test.com");

		return new UserDto(
			userId,
			"name" + userId,
			email,
			nickname,
			"oauthProvider",
			"accessToken",
			Grade.USER,
			LocalDateTime.now(),
			LocalDateTime.now()
		);
	}

	private UserEntity getUserEntity(Long userId) {
		Nickname nickname = new Nickname("nickname" + userId);
		Email email = new Email("email" + userId + "@test.com");

		return UserEntity.builder()
			.id(userId)
			.name("name" + userId)
			.email(email)
			.nickname(nickname)
			.oauthProvider("oauthProvider")
			.accessToken("accessToken")
			.grade(Grade.USER)
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();
	}

	private UserCreateDto getUserCreateDto(Long userId) {
		Nickname nickname = new Nickname("nickname" + userId);
		Email email = new Email("email" + userId + "@test.com");

		return new UserCreateDto(
			"name" + userId,
			email,
			nickname,
			"oauthProvider",
			"accessToken",
			Grade.USER
		);
	}

	private UserUpdateDto getUserUpdateDto(Long userId) {
		Nickname nickname = new Nickname("nickname" + userId);
		return new UserUpdateDto(userId, nickname);
	}

	@Test
	@DisplayName("유저 조회 테스트 (정상 케이스)")
	void getUser() {
		// given
		UserEntity saved = getUserEntity(1L);
		given(userRepository.findById(anyLong())).willReturn(Optional.of(saved));
		// when
		UserDto actual = userService.getUser(1L);
		// then
		verify(userRepository, times(1)).findById(1L);
		assertThat(actual.userId()).isEqualTo(1L);
	}

	@Test
	@DisplayName("유저 조회시 유저가 없는 경우 UserNotFoundException 발생")
	void getUser_UserNotFoundException() {
		// given
		given(userRepository.findById(anyLong())).willReturn(Optional.empty());
		// when & then
		assertThrows(UserNotFoundException.class, () -> {
			userService.getUser(1L);
		});
	}

	@Test
	@DisplayName("유저 생성 테스트 (정상 케이스)")
	void createUser() {
		// given
		UserCreateDto userCreateDto = getUserCreateDto(1L);
		UserEntity user = getUserEntity(1L);
		given(userRepository.save(any(UserEntity.class))).willReturn(user);
		// when
		UserDto actual = userService.createUser(userCreateDto);
		// then
		verify(userRepository, times(1)).save(any(UserEntity.class));
		assertThat(actual.name()).isEqualTo("name1");
		assertThat(actual.email()).isEqualTo(userCreateDto.email());
		assertThat(actual.nickname()).isEqualTo(userCreateDto.nickname());
	}

	@Test
	@DisplayName("유저 생성시 닉네임 중복인 경우 DuplicatedNicknameException 발생")
	void createUser_DuplicatedNicknameException() {
		// given
		UserCreateDto userCreateDto = getUserCreateDto(1L);
		UserEntity user = getUserEntity(1L);
		given(userRepository.existsByNickname(any(Nickname.class))).willReturn(true);
		// when & then
		assertThrows(DuplicatedNicknameException.class, () -> {
			userService.createUser(userCreateDto);
		});
	}

	@Test
	@DisplayName("유저 수정 테스트 (정상 케이스)")
	void updateUser() {
		// given
		UserEntity user = getUserEntity(1L);
		UserUpdateDto userUpdateDto = UserUpdateDto.builder()
			.userId(1L)
			.nickname(new Nickname("nicknameUpdated"))
			.build();
		given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
		// when
		UserDto actual = userService.updateUser(userUpdateDto);
		// then
		verify(userRepository, times(1)).findById(1L);
		assertThat(actual.userId()).isEqualTo(1L);
		assertThat(actual.nickname()).isEqualTo(userUpdateDto.nickname());
	}

	@Test
	@DisplayName("유저 수정시 닉네임 중복인 경우 DuplicatedNicknameException 발생")
	void updateUser_DuplicatedNicknameException() {
		// given
		UserEntity user = getUserEntity(1L);
		UserUpdateDto userUpdateDto = UserUpdateDto.builder()
			.userId(1L)
			.nickname(new Nickname("nicknameDuplicated"))
			.build();
		given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
		given(userRepository.existsByNickname(any(Nickname.class))).willReturn(true);
		// when & then
		assertThrows(DuplicatedNicknameException.class, () -> {
			userService.updateUser(userUpdateDto);
		});
	}

	@Test
	@DisplayName("유저 수정시 id가 없는 경우 UserNotFoundException 발생")
	void updateUser_UserNotFoundException() {
		// given
		UserUpdateDto userUpdateDto = getUserUpdateDto(1L);
		given(userRepository.findById(anyLong())).willReturn(Optional.empty());
		// when & then
		assertThrows(UserNotFoundException.class, () -> {
			userService.updateUser(userUpdateDto);
		});
	}

	@Test
	@DisplayName("유저 수정시 기존의 이름과 같은 경우 DuplicatedNicknameException 발생하지 않음")
	void updateUser_NotDuplicatedNicknameException() {
		// given
		UserEntity user = getUserEntity(1L);
		UserUpdateDto userUpdateDto = UserUpdateDto.builder()
			.userId(1L)
			.nickname(user.getNickname())
			.build();
		given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
		// when
		UserDto actual = userService.updateUser(userUpdateDto);
		// then
		verify(userRepository, times(1)).findById(1L);
		assertThat(actual.userId()).isEqualTo(1L);
		assertThat(actual.nickname()).isEqualTo(userUpdateDto.nickname());
	}

	@Test
	@DisplayName("유저 삭제 테스트 (정상 케이스)")
	void deleteUser() {
		// given
		UserEntity user = getUserEntity(1L);
		given(userRepository.existsById(anyLong())).willReturn(true);
		// when
		Long actual = userService.deleteUser(1L);
		// then
		verify(userRepository, times(1)).existsById(1L);
		verify(userRepository, times(1)).deleteById(1L);
		assertThat(actual).isEqualTo(1L);
	}

	@Test
	@DisplayName("유저 삭제시 유저가 없는 경우 UserNotFoundException 발생")
	void deleteUser_UserNotFoundException() {
		// given
		given(userRepository.existsById(anyLong())).willReturn(false);
		// when & then
		assertThrows(UserNotFoundException.class, () -> {
			userService.deleteUser(1L);
		});
	}

	@Test
	@DisplayName("닉네임 중복 체크 테스트 (중복인 경우)")
	void isDuplicatedNickname_True() {
		// given
		Nickname nickname = new Nickname("nickname");
		given(userRepository.existsByNickname(any(Nickname.class))).willReturn(true);
		// when
		boolean actual = userService.isDuplicatedNickname(nickname);
		// then
		verify(userRepository, times(1)).existsByNickname(nickname);
		assertThat(actual).isTrue();
	}

	@Test
	@DisplayName("닉네임 중복 체크 테스트 (중복이 아닌 경우)")
	void isDuplicatedNickname_False() {
		// given
		Nickname nickname = new Nickname("nickname");
		given(userRepository.existsByNickname(any(Nickname.class))).willReturn(false);
		// when
		boolean actual = userService.isDuplicatedNickname(nickname);
		// then
		verify(userRepository, times(1)).existsByNickname(nickname);
		assertThat(actual).isFalse();
	}

}
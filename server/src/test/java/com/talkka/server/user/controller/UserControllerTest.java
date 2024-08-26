package com.talkka.server.user.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.talkka.server.oauth.domain.NaverOAuth2User;
import com.talkka.server.oauth.domain.OAuth2UserInfo;
import com.talkka.server.oauth.enums.AuthRole;
import com.talkka.server.user.dto.UserCreateReqDto;
import com.talkka.server.user.dto.UserDto;
import com.talkka.server.user.dto.UserRespDto;
import com.talkka.server.user.dto.UserUpdateDto;
import com.talkka.server.user.dto.UserUpdateReqDto;
import com.talkka.server.user.exception.DuplicatedNicknameException;
import com.talkka.server.user.exception.UserNotFoundException;
import com.talkka.server.user.service.UserService;
import com.talkka.server.user.vo.Email;
import com.talkka.server.user.vo.Nickname;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
	@InjectMocks
	private UserController userController;

	@Mock
	private UserService userService;

	private OAuth2UserInfo getOAuth2UserInfo(Long userId, AuthRole authRole) {
		Map<String, Object> attributes = Map.of(
			"sub", userId,
			"name", "name" + userId,
			"userId", userId,
			"OAuth2Id", "OAuth2Id" + userId,
			"email", "email" + userId + "@test.com",
			"nickname", "nickname" + userId,
			"accessToken", "accessToken" + userId,
			"authRole", authRole.name(),
			"provider", "NAVER"
		);
		var grant = new SimpleGrantedAuthority(authRole.name());
		return new NaverOAuth2User(attributes, List.of(grant));
	}

	private UserDto getUserDto(Long userId) {
		Nickname nickname = new Nickname("nickname" + userId);
		Email email = new Email("email" + userId + "@test.com");

		return UserDto.builder()
			.userId(userId)
			.name("name" + userId)
			.email(email)
			.nickname(nickname)
			.oauthProvider("oauthProvider")
			.accessToken("accessToken")
			.authRole(AuthRole.USER)
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();
	}

	private UserRespDto getUserRespDto(Long userId) {
		Nickname nickname = new Nickname("nickname" + userId);
		Email email = new Email("email" + userId + "@test.com");

		return UserRespDto.builder()
			.userId(userId)
			.name("name" + userId)
			.email(email)
			.nickname(nickname)
			.build();
	}

	private UserUpdateReqDto getUserUpdateReqDto(Long userId) {
		return new UserUpdateReqDto("nickname" + userId);
	}

	private UserCreateReqDto getUserCreateReqDto(Long userId) {
		return new UserCreateReqDto("name" + userId);
	}

	@Test
	@DisplayName("사용자 정보 조회 (정상)")
	void getUserInfo() {
		// given
		Long userId = 1L;
		var userDto = getUserDto(userId);
		var userRespDto = UserRespDto.of(userDto);
		var expected = ResponseEntity.ok(userRespDto);
		given(userService.getUser(userId)).willReturn(userDto);
		// when
		var result = userController.getUser(userId);
		// then
		verify(userService).getUser(userId);
		assertThat(result).isEqualTo(expected);
	}

	@Test
	@DisplayName("존재하지 않는 사용자를 조회하면 400 에러 발생")
	void getUserInfoNotFound() {
		// given
		Long userId = 1L;
		var expected = ResponseEntity.badRequest().body("존재하지 않는 유저입니다.");
		given(userService.getUser(userId)).willThrow(new UserNotFoundException());
		// when
		var result = userController.getUser(userId);
		// then
		verify(userService).getUser(userId);
		assertThat(result).isEqualTo(expected);
	}

	@Test
	@DisplayName("특정 아이디 정보 수정 (정상)")
	void updateUserInfo() {
		// given
		Long userId = 1L;
		var userDto = getUserDto(userId);
		var userRespDto = UserRespDto.of(userDto);
		var userUpdateReqDto = new UserUpdateReqDto("nickname" + userId);
		var userUpdateDto = UserUpdateDto.of(userId, userUpdateReqDto);
		var expected = ResponseEntity.ok(userRespDto);

		given(userService.updateUser(userUpdateDto)).willReturn(userDto);
		// when
		var result = userController.updateUser(userId, userUpdateReqDto);
		// then
		verify(userService).updateUser(userUpdateDto);
		assertThat(result).isEqualTo(expected);
	}

	@Test
	@DisplayName("존재하지 않는 사용자를 수정하면 400 에러 발생")
	void updateUserInfoNotFound() {
		// given
		Long userId = 1L;
		var userUpdateReqDto = new UserUpdateReqDto("nickname" + userId);
		var expected = ResponseEntity.badRequest().body("존재하지 않는 유저입니다.");
		given(userService.updateUser(any())).willThrow(new UserNotFoundException());
		// when
		var result = userController.updateUser(userId, userUpdateReqDto);
		// then
		verify(userService).updateUser(any());
		assertThat(result).isEqualTo(expected);
	}

	@Test
	@DisplayName("Nickname이 중복되면 400 에러 발생")
	void updateUserInfoDuplicatedNickname() {
		// given
		Long userId = 1L;
		var userUpdateReqDto = new UserUpdateReqDto("duplicatedNickname");
		var userUpdateDto = UserUpdateDto.of(userId, userUpdateReqDto);
		var expected = ResponseEntity.badRequest().body("이미 존재하는 닉네임입니다.");
		given(userService.updateUser(userUpdateDto)).willThrow(new DuplicatedNicknameException());
		// when
		var result = userController.updateUser(userId, userUpdateReqDto);
		// then
		verify(userService).updateUser(userUpdateDto);
		assertThat(result).isEqualTo(expected);
	}

	@Test
	@DisplayName("Nickname 형식이 잘못되면 400 에러 발생")
	void updateUserInfoInvalidNickname() {
		// given
		Long userId = 1L;
		var userUpdateReqDto = new UserUpdateReqDto("INVALID_NICKNAME");
		var expected = ResponseEntity.badRequest().body("닉네임은 2자 이상 20자 이하의 영문, 숫자, 한글만 사용 가능합니다.");
		// when
		var result = userController.updateUser(userId, userUpdateReqDto);
		// then
		assertThat(result).isEqualTo(expected);
	}

	@Test
	@DisplayName("사용자 삭제 (정상)")
	void deleteUser() {
		// given
		Long userId = 1L;
		var expected = ResponseEntity.ok(userId);
		given(userService.deleteUser(userId)).willReturn(userId);
		// when
		var result = userController.deleteUser(userId);
		// then
		verify(userService).deleteUser(userId);
		assertThat(result).isEqualTo(expected);
	}

	@Test
	@DisplayName("존재하지 않는 사용자를 삭제하면 400 에러 발생")
	void deleteUserNotFound() {
		// given
		Long userId = 1L;
		var expected = ResponseEntity.badRequest().body("존재하지 않는 유저입니다.");
		given(userService.deleteUser(userId)).willThrow(new UserNotFoundException());
		// when
		var result = userController.deleteUser(userId);
		// then
		verify(userService).deleteUser(userId);
		assertThat(result).isEqualTo(expected);
	}

	@Test
	@DisplayName("내 정보 조회 (정상)")
	void getMyInfo() {
		// given
		Long userId = 1L;
		var userDto = getUserDto(userId);
		var userRespDto = UserRespDto.of(userDto);
		var expected = ResponseEntity.ok(userRespDto);
		var userInfo = getOAuth2UserInfo(userId, AuthRole.USER);
		given(userService.getUser(userId)).willReturn(userDto);
		// when
		var result = userController.getMe(userInfo);
		// then
		verify(userService).getUser(userId);
		assertThat(result).isEqualTo(expected);
	}

	@Test
	@DisplayName("내 정보 조회 (권한 없음)")
	void getMyInfoNoAuth() {
		// given
		Long userId = 1L;
		var userInfo = getOAuth2UserInfo(userId, AuthRole.ADMIN);
		var expected = new ResponseEntity<>("권한이 없습니다.", HttpStatus.UNAUTHORIZED);
		given(userService.getUser(userId)).willThrow(new UserNotFoundException());
		// when
		var result = userController.getMe(userInfo);
		// then
		assertThat(result).isEqualTo(expected);
	}

	@Test
	@DisplayName("내 정보 수정 (정상)")
	void updateMyInfo() {
		// given
		Long userId = 1L;
		var userDto = getUserDto(userId);
		var userUpdateReqDto = getUserUpdateReqDto(userId);
		var userUpdateDto = UserUpdateDto.of(userId, userUpdateReqDto);
		var userRespDto = UserRespDto.of(userDto);
		var expected = ResponseEntity.ok(userRespDto);
		var userInfo = getOAuth2UserInfo(userId, AuthRole.USER);
		given(userService.updateUser(userUpdateDto)).willReturn(userDto);
		// when
		var result = userController.updateMe(userInfo, userUpdateReqDto);
		// then
		verify(userService).updateUser(userUpdateDto);
		assertThat(result).isEqualTo(expected);
	}

	@Test
	@DisplayName("내 정보 수정 (권한 없음)")
	void updateMyInfoNoAuth() {
		// given
		Long userId = 1L;
		var userUpdateReqDto = getUserUpdateReqDto(userId);
		var userInfo = getOAuth2UserInfo(userId, AuthRole.ADMIN);
		var expected = new ResponseEntity<>("권한이 없습니다.", HttpStatus.UNAUTHORIZED);
		given(userService.updateUser(any())).willThrow(new UserNotFoundException());
		// when
		var result = userController.updateMe(userInfo, userUpdateReqDto);
		// then
		assertThat(result).isEqualTo(expected);
	}

	@Test
	@DisplayName("내 정보 수정시 닉네임이 중복되면 400 에러 발생")
	void updateMyInfoDuplicatedNickname() {
		// given
		Long userId = 1L;
		var userUpdateReqDto = getUserUpdateReqDto(userId);
		var userUpdateDto = UserUpdateDto.of(userId, userUpdateReqDto);
		var userInfo = getOAuth2UserInfo(userId, AuthRole.USER);
		var expected = ResponseEntity.badRequest().body("이미 존재하는 닉네임입니다.");
		given(userService.updateUser(userUpdateDto)).willThrow(new DuplicatedNicknameException());
		// when
		var result = userController.updateMe(userInfo, userUpdateReqDto);
		// then
		verify(userService).updateUser(userUpdateDto);
		assertThat(result).isEqualTo(expected);
	}

	@Test
	@DisplayName("내 정보 수정시 닉네임 형식이 잘못되면 400 에러 발생")
	void updateMyInfoInvalidNickname() {
		// given
		Long userId = 1L;
		var userUpdateReqDto = new UserUpdateReqDto("INVALID_NICKNAME");
		var userInfo = getOAuth2UserInfo(userId, AuthRole.USER);
		var expected = ResponseEntity.badRequest().body("닉네임은 2자 이상 20자 이하의 영문, 숫자, 한글만 사용 가능합니다.");
		// when
		var result = userController.updateMe(userInfo, userUpdateReqDto);
		// then
		assertThat(result).isEqualTo(expected);
	}

}